package com.project.chatop.controller;

import com.project.chatop.dto.response.ErrorResponse;
import com.project.chatop.dto.response.ErrorsResponse;
import com.project.chatop.port.service.AuthService;
import com.project.chatop.dto.request.LoginRequest;
import com.project.chatop.dto.request.RegisterRequest;
import com.project.chatop.dto.response.AuthResponse;
import com.project.chatop.mapper.UserMapper;
import com.project.chatop.port.service.UserService;
import com.project.chatop.entity.User;
import com.project.chatop.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        log.info("call /register");
        AuthResponse authResponse = authService.setRegister(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
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
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("call /login");
        return authService.setLogin(loginRequest);
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
    public UserResponse me(@AuthenticationPrincipal Long userId) {
        log.info("call /me");
        User user = userService.getUser(userId);
        return userMapper.toUserResponse(user);
    }

}
