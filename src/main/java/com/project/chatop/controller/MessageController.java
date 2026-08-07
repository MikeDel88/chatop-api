package com.project.chatop.controller;

import com.project.chatop.doc.ApiMessagesResponse;
import com.project.chatop.dto.response.ConfirmResponse;
import com.project.chatop.port.service.MessageService;
import com.project.chatop.dto.request.MessageRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Log4j2
@Tag(name = "Messages", description = "Gestion de l'envoi des messages vers le propriétaire de la location.")
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ApiMessagesResponse
    @PostMapping
    public ResponseEntity<ConfirmResponse> sendMessage(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody MessageRequest messageRequest
    ) {
        log.info("call /messages");
        messageService.create(messageRequest, Long.valueOf(Objects.requireNonNull(jwt.getSubject())));
        ConfirmResponse confirmResponse = new ConfirmResponse("Message send with success");
        return ResponseEntity.status(HttpStatus.CREATED).body(confirmResponse);
    }
}
