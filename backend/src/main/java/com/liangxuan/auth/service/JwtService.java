package com.liangxuan.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final SecretKey signingKey;
    private final long expirationHours;

    public JwtService(@Value("${liangxuan.jwt.secret}") String secret,
                      @Value("${liangxuan.jwt.expiration-hours:168}") long expirationHours) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT_SECRET must contain at least 32 characters");
        }
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationHours = expirationHours;
    }

    public String createToken(Long userId) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(Duration.ofHours(expirationHours))))
                .signWith(signingKey)
                .compact();
    }

    public Long parseUserId(String token) {
        return Long.valueOf(Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload().getSubject());
    }
}
