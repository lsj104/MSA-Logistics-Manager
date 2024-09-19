package com.team12.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //Refresh Token 저장
    public void saveRefreshToken(String username, String refreshToken) {
        redisTemplate.opsForValue().set(username, refreshToken, 14, TimeUnit.DAYS); //유효기간
    }

    //Refresh Token 조회
    public String getRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    //Refresh Token 삭제
    public void deleteRefreshToken(String username) {
        redisTemplate.delete(username);
    }
}
