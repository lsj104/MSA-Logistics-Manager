package com.team12.user.jwt;

import com.team12.user.domain.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.netty.util.internal.StringUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

//util 클래스: 특정한 매개변수, 파라미터에 대한 작업을 수행하는 메서드들이 존재하는 클래스, 다른 객체에 의존하지 않고 하나의 모듈로서 동작하는 클래스
@Component
public class JwtUtil {
    // 하단의 방법 : 직접 Cookie 만들고 Cookie에 JWT 담아서 보냄. -> 장점 : Custom 용이, header에 Set-Cookie로 자동으로 넘어감 // 이 방법 외에는 헤더에 넣어서 보내는 방법이 있음 -> 장점 : 코드 수 감소 // 더 좋고 나쁘고는 없으니, 상황에 맞게 구현하면 됨.
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; //60min

    @Value("${jwt.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");


    @PostConstruct // 딱 한번만 호출하면 되는 자원에 씀. 또 호출하는 것 방지
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();
        //암호화
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) //발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }
    // 생성된 JWT를 Cookie에 저장
    public void addJwtToCookie(String token, HttpServletResponse httpServletResponse) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");

            //Response 객체에 Cookie 추가
            httpServletResponse.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
    // Cookie에 들어있던 JWT 토큰을 Substring ("Bearer " 제거)
    public String substringToken(String tokenValue) {
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT Signature, 유효하지 않은 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token, 만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
        } return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInformationToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
