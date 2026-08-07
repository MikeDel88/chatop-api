package com.project.chatop.port.service;

import com.project.chatop.dto.response.AuthResponse;
import com.project.chatop.dto.request.LoginRequest;
import com.project.chatop.dto.request.RegisterRequest;

/**
 * Service qui gère l'authentification et l'accès au profil de l'utilisateur connecté.
 */
public interface AuthService {
    /**
     * Enregistrement de l'utilisateur.
     * On mappe les données de RegisterRequest vers User en encodant le mot de passe.
     * On enregistre l'utilisateur en base de données.
     * Si l'authentification est bonne, on crée un token et on le renvoie dans AuthResponse.
     * @param registerRequest DTO d'entrée pour l'enregistrement.
     * @return AuthResponse
     */
    AuthResponse setRegister(RegisterRequest registerRequest);

    /**
     * Login de l'utilisateur.
     * On cherche si l'utilisateur existe en base de données.
     * On regarde l'authentification via isAuthenticated (que l'user soit null ou pas, on applique la même logique pour éviter de savoir si l'utilisateur existe ou pas).
     * Si l'authentification est bonne, on crée un token et on le renvoie dans AuthResponse.
     * @param loginRequest DTO d'entrée pour la connexion.
     * @return AuthResponse
     */
    AuthResponse setLogin(LoginRequest loginRequest);
}

