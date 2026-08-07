package com.project.chatop.dto.response;

/**
 * Réponse au client d'un rental
 * @param id
 * @param name
 * @param surface
 * @param price
 * @param picture url pour accéder à l'image.
 * @param description
 * @param owner_id id du propriétaire du rental
 * @param created_at
 * @param updated_at
 */
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
