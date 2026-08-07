package com.project.chatop.exception;

import com.project.chatop.dto.response.ErrorResponse;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;

@Log4j2
@Order()
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        log.error("handleValidation : {}", ex.getMessage());
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new ErrorResponse(f.getField() + " " + f.getDefaultMessage()))
                .toList();
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setProperties(Map.of("errors", errors));
        return pd;
    }

    @ExceptionHandler({ValidationException.class, DataAccessException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail  handleDatabaseException(Exception exception) {
        log.error("handleDatabaseException : {}", exception.getMessage());
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setDetail("Une erreur est survenue en base de données.");
        return pd;
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ProblemDetail handlerRentalNotFound(RentalNotFoundException exception) {
        log.error("handlerRentalNotFound : {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handlerUserNotFound(UserNotFoundException exception) {
        log.error("handleUserNotFound : {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.error("handleMethodArgumentTypeMismatch : {}", exception.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("handleGenericException : {}", ex.getMessage());
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,  ex.getMessage());
    }
}
