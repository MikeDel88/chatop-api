package com.project.chatop.features.messages.web.controllers;

import com.project.chatop.common.web.dtos.ConfirmResponse;
import com.project.chatop.features.messages.application.services.MessageService;
import com.project.chatop.features.messages.web.dtos.MessageRequest;
import com.project.chatop.features.messages.web.exceptions.MessageNotCreatedException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<ConfirmResponse> sendMessage(@Valid @RequestBody MessageRequest messageRequest) {
        if(messageService.create(messageRequest) != null) {
            ConfirmResponse confirmResponse = new ConfirmResponse("Message send with success");
            return ResponseEntity.status(HttpStatus.CREATED).body(confirmResponse);
        } else {
            throw new MessageNotCreatedException();
        }
    }
}
