package com.project.chatop.exception;


import com.project.chatop.controller.RentalController;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.InvalidPathException;

@Log4j2
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(assignableTypes = RentalController.class)
public class RentalExceptionHandler {

    @ExceptionHandler(RentalNotFoundException.class)
    public ProblemDetail handlerRentalNotFound(RentalNotFoundException exception) {
        log.error("handleRentalNotFound : {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({IOException.class, InvalidPathException.class})
    public ProblemDetail handlerIOException(Exception exception) {
        log.error("handlerIOException : {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Un problème est survenue lors de l'enregistrement de l'image");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handlerIllegalArgumentException(IllegalArgumentException exception) {
        log.error("handlerIllegalArgumentException : {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}
