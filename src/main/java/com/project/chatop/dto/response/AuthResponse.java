package com.project.chatop.dto.response;

/**
 * Réponse lors de l'authentification register ou login
 * @param token
 */
public record AuthResponse(
    String token
) {}
