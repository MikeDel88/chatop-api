package com.project.chatop.features.rentals.web.exceptions;


import com.project.chatop.features.rentals.web.controllers.RentalController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = RentalController.class)
public class RentalExceptionHandler {

    @ExceptionHandler(RentalNotFoundException.class)
    public ProblemDetail handlerRentalNotFound(RentalNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(RentalNotCreatedException.class)
    public ProblemDetail handlerRentalNotCreated(RentalNotCreatedException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(RentalNotUpdatedException.class)
    public ProblemDetail handlerRentalNotUpdated(RentalNotUpdatedException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

}
