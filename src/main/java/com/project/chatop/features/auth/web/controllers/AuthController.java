package com.project.chatop.features.auth.web.controllers;

import com.project.chatop.common.web.dtos.ErrorResponse;
import com.project.chatop.common.web.dtos.ErrorsResponse;
import com.project.chatop.features.auth.application.services.AuthService;
import com.project.chatop.features.auth.web.dtos.LoginRequest;
import com.project.chatop.features.auth.web.dtos.RegisterRequest;
import com.project.chatop.features.auth.web.dtos.AuthResponse;
import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.users.application.services.UserService;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.web.dtos.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @Operation(summary = "Créer un compte utilisateur")
    @SecurityRequirements()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'utilisateur a bien été crée et token envoyé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Body invalide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Problème lors de l'enregistrement",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
        }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.setRegister(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }

    @Operation(summary = "Se connecter à un compte utilisateur")
    @SecurityRequirements()
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur a bien été connecté et token envoyé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Body invalide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Problème lors de la connexion",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.setLogin(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @Operation(summary = "Récupération du profil de l'utilisateur connecté")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur a bien été envoyé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
    }
    )
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

}
