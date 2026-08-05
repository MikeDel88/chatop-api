package com.project.chatop.service;

import com.project.chatop.port.service.JwtService;
import com.project.chatop.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

     private final SecretKey secretKey;

    private final String keyType = "type";

    private enum JWT_TYPE {
        ACCESS
    }

    public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret) {
        byte[] jwtDecode = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(jwtDecode);
    }

    public String generateAccessToken(String userId) {
        Long expirationTime = 30L * 24 * 60 * 60 * 1000L;
        return generateToken(userId, expirationTime);
    }

    public Boolean validateAccessToken(String token) {
        Claims claims = this.parseAllClaims(token);
        if(claims == null)
            return false;

        if(!claims.containsKey(this.keyType) || !(claims.get(this.keyType) instanceof String tokenType))
            return false;

        return tokenType.equalsIgnoreCase(JWT_TYPE.ACCESS.name());
    }

    public String getUserIdFromToken(String token) {
        Claims claims = this.parseAllClaims(token);

        if(claims == null)
            throw new InvalidTokenException("l'authentification est invalide.");

        return claims.getSubject();
    }

    private String generateToken(String subject, Long expiry) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry);
        return Jwts.builder()
                .subject(subject)
                .claim(this.keyType, JWT_TYPE.ACCESS)
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
