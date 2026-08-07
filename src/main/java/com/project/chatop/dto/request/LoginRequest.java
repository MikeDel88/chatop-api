package com.project.chatop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Requete lors du login de l'utilisateur "/auth/login"
 * @param email
 * @param password
 */
public record LoginRequest(
    @Email
    String email,
    @NotBlank @Size(min = 3)
    String password
) {}
