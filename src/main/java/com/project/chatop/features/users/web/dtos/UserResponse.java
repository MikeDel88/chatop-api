package com.project.chatop.features.users.web.dtos;

public record UserResponse(
        Long id,
        String name,
        String email,
        String created_at,
        String updated_at
) {
}
