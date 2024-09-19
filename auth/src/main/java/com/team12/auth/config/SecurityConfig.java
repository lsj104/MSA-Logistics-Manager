package com.team12.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> {
                    cors.configurationSource((request) -> {
                        var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(java.util.List.of("*"));
                        corsConfiguration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE"));
                        corsConfiguration.setAllowedHeaders(java.util.List.of("*"));
                        return corsConfiguration;
                    });
                })
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .anyRequest().permitAll() // 그 외 모든 요청 인증처리
                )
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//
        return http.build();
    }
}
