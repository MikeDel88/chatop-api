package com.project.chatop.port.service;

/**
 * JwtService permet la génération du token d'accès de l'utilisateur.
 * La validation et le décodage du token sont désormais gérés nativement
 * par Spring Security (JwtDecoder / OAuth2 Resource Server).
 */
public interface JwtService {
    /**
     * On génère un token avec une expirationTime de 30 jours.
     * @param userId utilisé dans les claims comme subject.
     * @return String le token généré.
     */
    String generateAccessToken(String userId);
}
