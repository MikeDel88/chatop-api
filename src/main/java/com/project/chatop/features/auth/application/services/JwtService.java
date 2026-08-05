package com.project.chatop.features.auth.application.services;

public interface JwtService {
    String generateAccessToken(String userId);
    Boolean validateAccessToken(String token);
    String getUserIdFromToken(String token);
}


