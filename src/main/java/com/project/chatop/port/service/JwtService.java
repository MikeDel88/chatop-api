package com.project.chatop.port.service;

/**
 * JwtService permet la génération du token.
 * Sa validité et récupérer le subject du token.
 */
public interface JwtService {
    /**
     * On génère un token avec une expirationTime de xx jours.
     * @param userId utilisé dans les claims comme subject.
     * @return String le token généré.
     */
    String generateAccessToken(String userId);
    /**
     * Vérification de la validité du token.
     * Si les claims ou le type n'est pas bon, on renvoi false.
     * @param token du header de la requête.
     * @return Boolean si le token est valide.
     */
    Boolean validateAccessToken(String token);
    /**
     * Récupère l'id subject inclus dans le token.
     * Lève une InvalidTokenException si les claims sont null.
     * @param token du header de la requête.
     * @return String l'identifiant Id subject.
     */
    String getUserIdFromToken(String token);
}


