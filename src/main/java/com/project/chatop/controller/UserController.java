package com.project.chatop.controller;

import com.project.chatop.dto.response.ErrorResponse;
import com.project.chatop.dto.response.ErrorsResponse;
import com.project.chatop.mapper.UserMapper;
import com.project.chatop.port.service.UserService;
import com.project.chatop.entity.User;
import com.project.chatop.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
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
                        schema = @Schema(implementation = ErrorsResponse.class)) }),
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
    public UserResponse getUser(@Positive @NotNull @PathVariable String id) {
        log.info("call /getUser id {}", id);
        User user = this.userService.getUser(Long.valueOf(id));
        return userMapper.toUserResponse(user);
    }
}
