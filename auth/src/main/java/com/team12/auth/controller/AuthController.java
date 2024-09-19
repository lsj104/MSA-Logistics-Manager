package com.team12.auth.controller;

import com.team12.auth.dto.JwtAuthenticationResponse;
import com.team12.auth.service.AuthService;
import com.team12.common.dto.auth.LoginRequestDto;
import com.team12.common.dto.auth.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final AuthService authService;


    //login
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginDto) {
        try {
            //header 설정
            JwtAuthenticationResponse response = authService.login(loginDto);
            LoginResponseDto loginResponseDto = new LoginResponseDto(response.getAccessToken(), response.getRefreshToken());

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-User-Id", String.valueOf(response.getUserId()));
            headers.set("X-User-Name", response.getUsername());
            headers.set("X-User-Role", response.getRole());


            return ResponseEntity.ok().headers(headers).body(loginResponseDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    //refreshToken refresh
    @GetMapping("/refresh-token")
    public ResponseEntity refreshToken(@RequestBody Map<String, String> tokenRequest) {
        String refreshToken = tokenRequest.get("refreshToken");
        JwtAuthenticationResponse response = authService.refreshToken(refreshToken);
        LoginResponseDto loginResponseDto = new LoginResponseDto(response.getAccessToken(), response.getRefreshToken());

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-Id", String.valueOf(response.getUserId()));
        headers.set("X-User-Name", response.getUsername());
        headers.set("X-User-Role", response.getRole());


        return ResponseEntity.ok().headers(headers).body(loginResponseDto);
    }




}
