package com.project.chatop.dto.response;

public record UserResponse(
        Long id,
        String name,
        String email,
        String created_at,
        String updated_at
) {
}
