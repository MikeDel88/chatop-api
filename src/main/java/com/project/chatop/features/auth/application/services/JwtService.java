package com.project.chatop.features.auth.application.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

     private final SecretKey secretKey;

    private final Long EXPIRATION_TIME = 30L * 24 * 60 * 60 * 1000L;

    public JwtService(@Value("${jwt.secret}") String jwtSecret) {
        byte[] jwtDecode = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(jwtDecode);
    }

    public String generateAccessToken(String userId) {
        return generateToken(userId, "access", EXPIRATION_TIME);
    }

    public Boolean validateAccessToken(String token) {
        Claims claims = this.parseAllClaims(token);
        if(claims == null)
            return false;

        if(!claims.containsKey("type") || !(claims.get("type") instanceof String tokenType))
            return false;

        return tokenType.equalsIgnoreCase("access");
    }

    private String generateToken(String subject, String type , Long expiry) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry);
        return Jwts.builder()
                .subject(subject)
                .claim("type", type)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    private Claims parseAllClaims(String token) {
        String rawToken = token;

        if(token.startsWith("Bearer ")) {
            rawToken = token.substring(7);
        }

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(rawToken)
                    .getPayload();
        } catch(Exception e) {
            return null;
        }
    }
}
