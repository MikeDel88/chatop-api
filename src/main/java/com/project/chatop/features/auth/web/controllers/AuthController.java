package com.project.chatop.features.auth.web.controllers;

import com.project.chatop.features.auth.application.services.AuthService;
import com.project.chatop.features.auth.web.dtos.LoginRequest;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import com.project.chatop.features.auth.web.dtos.AuthResponse;
import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.users.application.services.UserService;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.web.dtos.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.setRegister(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.setLogin(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

}
