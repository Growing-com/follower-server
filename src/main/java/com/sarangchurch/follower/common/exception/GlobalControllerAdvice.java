package com.sarangchurch.follower.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.sarangchurch.follower.common.exception.ErrorResponse.ValidationError;
import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;
import static com.sarangchurch.follower.common.exception.GlobalErrorCode.*;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return createErrorResponse(DB_INTEGRITY_VIOLATION, e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return createErrorResponse(BAD_PARAMETER, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return createErrorResponse(BAD_PARAMETER, e, createValidationErrors(e));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return createErrorResponse(BAD_PARAMETER, e);
    }

    private List<ValidationError> createValidationErrors(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(it -> new ValidationError(it.getField(), it.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
