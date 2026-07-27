package com.project.chatop.features.auth.application.services;

import com.project.chatop.features.auth.web.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

enum JWT_TYPE {
    ACCESS
}

enum JWT_ROLE {
    ADMIN,
    USER
}

@Service
public class JwtService {

     private final SecretKey secretKey;

    private final Long EXPIRATION_TIME = 30L * 24 * 60 * 60 * 1000L;

    private final String keyType = "type";
    private final String keyRole = "role";

    public JwtService(@Value("${jwt.secret}") String jwtSecret) {
        byte[] jwtDecode = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(jwtDecode);
    }

    public String generateAccessToken(String userId, JWT_ROLE role) {
        return generateToken(userId, JWT_TYPE.ACCESS, role, EXPIRATION_TIME);
    }

    public Boolean validateAccessToken(String token) {
        Claims claims = this.parseAllClaims(token);
        if(claims == null)
            return false;

        if(!claims.containsKey(this.keyType) || !(claims.get(this.keyType) instanceof JWT_TYPE tokenType))
            return false;

        if(!claims.containsKey(this.keyRole) || !(claims.get(this.keyRole) instanceof JWT_ROLE))
            return false;

        return tokenType == JWT_TYPE.ACCESS;
    }

    public String getUserIdFromToken(String token) {
        Claims claims = this.parseAllClaims(token);

        if(claims == null)
            throw new InvalidTokenException("l'authentification est invalide.");

        return claims.getSubject();
    }

    private String generateToken(String subject, JWT_TYPE type , JWT_ROLE role, Long expiry) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry);
        return Jwts.builder()
                .subject(subject)
                .claim(this.keyType, type)
                .claim(this.keyRole, role)
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
