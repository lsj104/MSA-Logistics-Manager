package com.team12.auth.jwt;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        String secretKey = SecretKeyGenerator.generateSecretKey(32);
        System.out.println("Generated SecretKey : " + secretKey);
    }

    public static String generateSecretKey(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[length];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }
}
