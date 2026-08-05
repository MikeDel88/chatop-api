package com.project.chatop.features.messages.web.exceptions;

import com.project.chatop.features.messages.web.controllers.MessageController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MessageController.class)
public class MessageExceptionHandler {

    @ExceptionHandler(MessageNotCreatedException.class)
    public ProblemDetail handlerMessageNotCreated(MessageNotCreatedException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

}
