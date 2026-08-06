package com.project.chatop.port.service;

import com.project.chatop.dto.response.AuthResponse;
import com.project.chatop.dto.request.LoginRequest;
import com.project.chatop.dto.request.RegisterRequest;

public interface AuthService {
    AuthResponse setRegister(RegisterRequest registerRequest);
    AuthResponse setLogin(LoginRequest loginRequest);
}

