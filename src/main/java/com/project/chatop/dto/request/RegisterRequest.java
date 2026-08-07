package com.project.chatop.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Requete lors de l'enregistrement de l'utilisateur "/auth/register"
 * Tout est required
 * @param name nom de l'utilisateur
 * @param email
 * @param password
 */
public record RegisterRequest(
    @NotBlank
    @Size(min = 3, message = "le nom doit contenir au minimum 3 caractères.")
    String name,
    @Email(message = "Le format de l'email est invalide.")
    String email,
    @NotBlank
    @Size(min = 3, message = "le mot de passe doit contenir au minimum 3 caractères.")
    String password
) {}
