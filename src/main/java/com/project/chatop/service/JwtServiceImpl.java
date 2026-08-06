package com.project.chatop.service;

import com.project.chatop.port.service.JwtService;
import com.project.chatop.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JwtService permet la génération du token.
 * Sa validité et récupérer le subject du token.
 */
@Log4j2
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

    /**
     * On génère un token avec une expirationTime de xx jours.
     * @param userId utilisé dans les claims comme subject.
     * @return String le token généré.
     */
    public String generateAccessToken(String userId) {
        log.info("JWT Service : generateAccessToken");
        Long expirationTime = 30L * 24 * 60 * 60 * 1000L;
        String token = generateToken(userId, expirationTime);
        log.debug("JWT Service : Token de l'utilisateur {}", token);
        return token;
    }

    /**
     * Vérification de la validité du token.
     * Si les claims ou le type n'est pas bon, on renvoi false.
     * @param token du header de la requête.
     * @return Boolean si le token est valide.
     */
    public Boolean validateAccessToken(String token) {
        log.info("JWT Service : validateAccessToken");

        Claims claims = this.parseAllClaims(token);
        log.debug("JWT Service : Claims de l'utilisateur {}", claims);

        if(claims == null)
            return false;

        if(!claims.containsKey(this.keyType) || !(claims.get(this.keyType) instanceof String tokenType))
            return false;

        return tokenType.equalsIgnoreCase(JWT_TYPE.ACCESS.name());
    }

    /**
     * Récupère l'id subject inclus dans le token.
     * Lève une InvalidTokenException si les claims sont null.
     * @param token du header de la requête.
     * @return String l'identifiant Id subject.
     */
    public String getUserIdFromToken(String token) {
        log.info("JWT Service : getUserIdFromToken");
        Claims claims = this.parseAllClaims(token);

        if(claims == null)
            throw new InvalidTokenException();

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
