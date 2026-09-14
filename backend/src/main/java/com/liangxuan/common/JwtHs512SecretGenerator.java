package com.liangxuan.common;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtHs512SecretGenerator {
    public static void main(String[] args) {
        // HS512 requires 64 raw bytes (512 bits)
        byte[] rawKey = new byte[64];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(rawKey);

        // JWT standard: Base64URL without padding
        String jwtSecret = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(rawKey);

        System.out.println("==== JWT HS512 Secret (JWT_SECRET) ====");
        System.out.println(jwtSecret);
        System.out.println("Length: " + jwtSecret.length());
    }
}
