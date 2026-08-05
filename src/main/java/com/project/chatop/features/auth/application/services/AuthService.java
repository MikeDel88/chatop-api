package com.project.chatop.features.auth.application.services;

import com.project.chatop.features.auth.web.dtos.AuthResponse;
import com.project.chatop.features.auth.web.dtos.LoginRequest;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;

public interface AuthService {
    AuthResponse setRegister(RegisterRequest registerRequest);
    AuthResponse setLogin(LoginRequest loginRequest);
}

