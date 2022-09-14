package com.sarangchurch.follower.common.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.sarangchurch.follower.common.exception.ErrorResponse.*;
import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.*;
import static com.sarangchurch.follower.common.exception.GlobalErrorCode.*;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return createErrorResponse(DATABASE_FAILURE, e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgsException(IllegalArgumentException e) {
        return createErrorResponse(BAD_REQUEST, e);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConversionFailedException(ConversionFailedException e) {
        return createErrorResponse(CONVERSION_FAILURE, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return createErrorResponse(VALIDATION_FAILURE, e, createValidationErrors(e));
    }

    private List<ValidationError> createValidationErrors(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(it -> new ValidationError(it.getField(), it.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
