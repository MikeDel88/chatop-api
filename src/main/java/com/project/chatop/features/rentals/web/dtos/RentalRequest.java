package com.project.chatop.features.rentals.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;

public record RentalRequest(
        @NotBlank String name,
        @NotNull @Positive Integer surface,
        @NotNull @Positive Integer price,
        MultipartFile picture,
        @NotBlank String description
) {}
