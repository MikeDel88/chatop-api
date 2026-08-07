package com.project.chatop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Requete lors de l'envoi d'un message "/messages"
 * Tout doit être required
 * @param user_id utilisateur en cours de session
 * @param rental_id le rental concerné
 * @param message le message à enregistrer
 */
public record MessageRequest(
        @NotNull @Positive Long user_id,
        @NotNull @Positive Long rental_id,
        @NotBlank String message
) {}
