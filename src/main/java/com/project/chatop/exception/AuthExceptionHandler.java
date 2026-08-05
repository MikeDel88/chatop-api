package com.project.chatop.exception;


import com.project.chatop.port.service.JwtService;
import com.project.chatop.controller.AuthController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Soit par Order, soit par packages (basePackages = "com.exemple.app.directory")
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
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
