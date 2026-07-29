package com.project.chatop.features.users.web.controllers;

import com.project.chatop.common.web.dtos.ErrorResponse;
import com.project.chatop.features.users.application.mappers.UserMapper;
import com.project.chatop.features.users.application.services.UserService;
import com.project.chatop.features.users.domain.entities.User;
import com.project.chatop.features.users.web.dtos.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@Tag(name = "Users", description = "Gestion des utilisateurs")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Récupération des informations du propriétaire par son identifiant pour une location.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "L'utilisateur a bien été envoyé",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)) }),
        @ApiResponse(responseCode = "400", description = "Id invalide",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = MethodArgumentNotValidException.class)) }),
        @ApiResponse(responseCode = "404", description = "Utilisateur introuvable",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ErrorResponse.class)) }),
        @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                content = @Content),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@Valid @Positive @NotNull @PathVariable String id) {
        User user = this.userService.getUser(Long.valueOf(id));
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }
}
