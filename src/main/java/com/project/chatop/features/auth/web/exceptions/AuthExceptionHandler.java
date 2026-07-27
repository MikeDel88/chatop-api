package com.project.chatop.features.auth.web.exceptions;


import com.project.chatop.features.auth.web.controllers.AuthController;
import com.project.chatop.common.web.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthController.class)
public class AuthExceptionHandler {

    @ExceptionHandler(BadAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleBadAuthentication(BadAuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage()));
    }

}
