package com.team12.api_gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
@Slf4j
public class JwtFilter implements GlobalFilter {

    @Value("${jwt.key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (path.equals("/api/auth/login") || path.equals("/api/users/sign-up") || path.equals("api/auth/refresh-token")) {
            return chain.filter(exchange);
        }

        String token = extractToken(exchange);

        if (token == null || !validateToken(token, exchange)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            log.info(exchange.getRequest().getURI().getPath());
            return onError(exchange, "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        String role = getRole(token);
        String userId = getUserId(token);  // ID 추출 메서드
        String username = getUsername(token);  // 사용자명 추출 메서드

        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(r -> r.headers(headers -> {
                    headers.add("X-User-Id", userId);
                    headers.add("X-User-Name", username);
                    headers.add("X-User-Role", role);
                }))
                .build();

        return chain.filter(modifiedExchange);
    }

    //username 추출
    private String getUsername(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }
    //권한 추출
    private String getRole(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role", String.class);
    }

    //id 추출
    private String getUserId(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("id", String.class);
    }

    //Claims 추출
    private Claims getClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claimsJws.getBody();
    }

    // 토큰 헤더에서 추출
    private String extractToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    //토큰 검증
    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");
        String body = "{\"error\": \"" + errorMsg + "\"}";
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(body.getBytes())));
    }
}

