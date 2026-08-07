package com.project.chatop.dto.response;

/**
 * Renvoi les informations d'un utilisateur "/me," "/user/:id"
 * @param id
 * @param name
 * @param email
 * @param created_at
 * @param updated_at
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        String created_at,
        String updated_at
) {
}
