package com.project.chatop.features.auth.web.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @Email
    String email,
    @NotBlank @Size(min = 3)
    String password
) {}
