package com.project.chatop.common.web.exceptions;

import com.project.chatop.common.web.dtos.ErrorResponse;
import com.project.chatop.common.web.dtos.ErrorsResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorsResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new ErrorResponse(f.getField() + " " + f.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ErrorsResponse(errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorsResponse>  handleConstraintViolation(ConstraintViolationException ex) {
        List<ErrorResponse> errors = ex.getConstraintViolations().stream()
                .map(v -> new ErrorResponse(v.getPropertyPath() + " " + v.getMessage()))
                .toList();

        return ResponseEntity.badRequest().body(new ErrorsResponse(errors));
    }
}
