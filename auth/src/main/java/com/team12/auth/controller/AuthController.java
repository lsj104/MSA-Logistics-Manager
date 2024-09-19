package com.team12.auth.controller;

import com.team12.auth.dto.JwtAuthenticationResponse;
import com.team12.auth.jwt.JwtTokenProvider;
import com.team12.auth.service.CustomUserDetailsService;
import com.team12.auth.service.RedisService;
import com.team12.auth.service.AuthService;
import com.team12.common.dto.auth.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisService redisService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthService authService;


    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtUtil;

    //login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginDto) {
        try {
            JwtAuthenticationResponse response = authService.login(loginDto);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }




}
