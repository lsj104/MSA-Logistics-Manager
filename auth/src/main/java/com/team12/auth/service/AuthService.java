package com.team12.auth.service;

import com.team12.auth.dto.CustomUserDetails;
import com.team12.auth.dto.JwtAuthenticationResponse;
import com.team12.auth.jwt.JwtTokenProvider;
import com.team12.common.dto.auth.LoginRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisService redisService;
    private final CustomUserDetailsService customUserDetailsService;


    public JwtAuthenticationResponse login(LoginRequestDto loginDto) throws AuthenticationException {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(),
                            loginDto.getPassword()
                    )
            );

            // AccessToken, RefreshToken 생성
            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);
            //사용자 정보
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getId();  // Long 타입의 ID
            String username = userDetails.getUsername();  // 사용자 이름
            String role = userDetails.getAuthorities().iterator().next().getAuthority().substring(5);


            // Redis에 RefreshToken 저장
            redisService.saveRefreshToken(authentication.getName(), refreshToken);

            // 응답 DTO 반환
            return new JwtAuthenticationResponse(accessToken, refreshToken, userId, username, role);
        }

    //refreshToken
    public JwtAuthenticationResponse refreshToken(String refreshToken) {
        //refreshToken 유효성 확인
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
            //Redis에서 refreshToken 확인
            String storedToken = redisService.getRefreshToken(username).substring(7);
            if (storedToken != null && storedToken.equals(refreshToken)) {
                //new accessToken
                // CustomUserDetailsService를 이용해 사용자 정보 가져오기
                CustomUserDetails userDetail = (CustomUserDetails) customUserDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
                //userDetails
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                Long userId = userDetails.getId();
                String userDetailsUsername = userDetails.getUsername();
                String role = userDetails.getAuthorities().iterator().next().getAuthority().substring(5);
                //return 새로운 accessToken과 기존 refreshToken
                return new JwtAuthenticationResponse(newAccessToken, refreshToken, userId, userDetailsUsername, role);
            } else {
                //Todo : exception
                throw new IllegalArgumentException("Invalid refresh Token");
            }
        }else {
            throw new IllegalArgumentException("Refresh token is expired or invalid");
        }
    }

}

