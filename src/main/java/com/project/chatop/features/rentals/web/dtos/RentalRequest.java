package com.project.chatop.features.rentals.web.dtos;

import org.springframework.web.multipart.MultipartFile;

public record RentalRequest(
        String name,
        Integer surface,
        Integer price,
        MultipartFile picture,
        String description
) {}
