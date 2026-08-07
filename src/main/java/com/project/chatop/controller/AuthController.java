package com.project.chatop.controller;

import com.project.chatop.doc.ApiLoginResponse;
import com.project.chatop.doc.ApiMeResponse;
import com.project.chatop.doc.ApiRegisterResponse;
import com.project.chatop.port.service.AuthService;
import com.project.chatop.dto.request.LoginRequest;
import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.dto.response.AuthResponse;
import com.project.chatop.mapper.UserMapper;
import com.project.chatop.port.service.UserService;
import com.project.chatop.entity.User;
import com.project.chatop.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@Log4j2
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Authentification de l'utilisateur et récupération du profil")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserService userService, UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @SecurityRequirements()
    @ApiRegisterResponse()
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("call /register");
        AuthResponse authResponse = authService.setRegister(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @SecurityRequirements()
    @ApiLoginResponse
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("call /login");
        return authService.setLogin(loginRequest);
    }

    @ApiMeResponse
    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Jwt jwt) {
        log.info("call /me");
        Long userId = Long.valueOf(Objects.requireNonNull(jwt.getSubject()));
        User user = userService.getUser(userId);
        return userMapper.toUserResponse(user);
    }

}
