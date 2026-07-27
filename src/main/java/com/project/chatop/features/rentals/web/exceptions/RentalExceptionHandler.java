package com.project.chatop.features.rentals.web.exceptions;


import com.project.chatop.common.web.dtos.ErrorResponse;
import com.project.chatop.features.rentals.web.controllers.RentalController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = RentalController.class)
public class RentalExceptionHandler {

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerRentalNotFound(RentalNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(RentalNotCreatedException.class)
    public ResponseEntity<ErrorResponse> handlerRentalNotCreated(RentalNotCreatedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(RentalNotUpdatedException.class)
    public ResponseEntity<ErrorResponse> handlerRentalNotUpdated(RentalNotUpdatedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(PictureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerRentalNotUpdated(PictureNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }


}
