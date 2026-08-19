package com.project.chatop.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Requete lors de l'envoi d'un message "/messages"
 * Tout doit être required
 * @param userId utilisateur en cours de session
 * @param rentalId le rental concerné
 * @param message le message à enregistrer
 */
public record MessageRequest(
        @NotNull @Positive @JsonProperty("user_id") Long userId,
        @NotNull @Positive @JsonProperty("rental_id") Long rentalId,
        @NotBlank String message
) {}
