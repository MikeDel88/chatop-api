package com.project.chatop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MessageRequest(
        @NotNull @Positive Long user_id,
        @NotNull @Positive Long rental_id,
        @NotBlank String message
) {}
