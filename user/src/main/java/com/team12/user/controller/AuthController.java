package com.team12.user.controller;

import com.team12.user.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtUtil jwtUtil;



}
