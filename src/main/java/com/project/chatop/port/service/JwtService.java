package com.project.chatop.port.service;

public interface JwtService {
    String generateAccessToken(String userId);
    Boolean validateAccessToken(String token);
    String getUserIdFromToken(String token);
}


