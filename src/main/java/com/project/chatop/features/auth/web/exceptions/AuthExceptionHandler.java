package com.project.chatop.features.auth.web.exceptions;


import com.project.chatop.features.auth.application.services.JwtService;
import com.project.chatop.features.auth.web.controllers.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {AuthController.class, JwtService.class})
public class AuthExceptionHandler {

    @ExceptionHandler(BadAuthenticationException.class)
    public ProblemDetail handleBadAuthentication(BadAuthenticationException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ProblemDetail handleInvalidToken(InvalidTokenException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

}
