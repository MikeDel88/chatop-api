package com.project.chatop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

/**
 * Requête lors d'un create ou update "/rentals"
 * Tout est requis. Lors d'un update, le picture peut être null si on ne souhaite pas le modifier.
 * @param name
 * @param surface integer
 * @param price integer
 * @param picture bytes
 * @param description
 */
public record RentalRequest(
        @NotBlank String name,
        @NotNull @Positive Integer surface,
        @NotNull @Positive Integer price,
        MultipartFile picture,
        @NotBlank String description
) {}
