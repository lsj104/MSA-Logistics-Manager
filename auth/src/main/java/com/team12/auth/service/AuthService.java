package com.team12.auth.service;

import com.team12.auth.dto.JwtAuthenticationResponse;
import com.team12.auth.jwt.JwtTokenProvider;
import com.team12.common.dto.auth.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisService redisService;

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

            // Redis에 RefreshToken 저장
            redisService.saveRefreshToken(authentication.getName(), refreshToken);

            // 응답 DTO 반환
            return new JwtAuthenticationResponse(accessToken, refreshToken);
        }

}
