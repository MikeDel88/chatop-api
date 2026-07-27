package com.project.chatop.features.rentals.web.dtos;

public record RentalResponse(
    Long id,
    String name,
    Integer surface,
    Integer price,
    String picture,
    String description,
    Long owner_id,
    String created_at,
    String updated_at
) {}
