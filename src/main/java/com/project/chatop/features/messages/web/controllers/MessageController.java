package com.project.chatop.features.messages.web.controllers;

import com.project.chatop.common.web.dtos.ConfirmResponse;
import com.project.chatop.common.web.dtos.ErrorResponse;
import com.project.chatop.common.web.dtos.ErrorsResponse;
import com.project.chatop.features.messages.application.services.MessageService;
import com.project.chatop.features.messages.web.dtos.MessageRequest;
import com.project.chatop.features.messages.web.exceptions.MessageNotCreatedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Messages", description = "Gestion de l'envoi des messages vers le propriétaire de la location.")
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Envoi de message au propriétaire de la location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Le message a été créée avec succès",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ConfirmResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Le Body est invalide",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Utilisateur non autorisé ou Message non créee",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Problème de réponse du serveur",
                    content = @Content),
    }
    )
    @PostMapping
    public ResponseEntity<ConfirmResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest) {
        if(messageService.create(messageRequest) == null) {
            throw new MessageNotCreatedException();
        }
        ConfirmResponse confirmResponse = new ConfirmResponse("Message send with success");
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmResponse);
    }
}
