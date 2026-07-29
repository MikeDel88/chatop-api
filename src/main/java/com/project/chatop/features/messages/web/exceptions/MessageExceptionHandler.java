package com.project.chatop.features.messages.web.exceptions;

import com.project.chatop.common.web.dtos.ErrorResponse;
import com.project.chatop.features.messages.web.controllers.MessageController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MessageController.class)
public class MessageExceptionHandler {

    @ExceptionHandler(MessageNotCreatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerMessageNotCreated(MessageNotCreatedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }

}
