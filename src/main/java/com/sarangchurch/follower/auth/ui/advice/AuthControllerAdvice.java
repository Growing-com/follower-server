package com.sarangchurch.follower.auth.ui.advice;

import com.sarangchurch.follower.auth.domain.exception.ForbiddenException;
import com.sarangchurch.follower.auth.domain.exception.RefreshTokenExpiredException;
import com.sarangchurch.follower.auth.domain.exception.RefreshTokenNotFoundException;
import com.sarangchurch.follower.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sarangchurch.follower.auth.ui.advice.AuthErrorCode.*;
import static com.sarangchurch.follower.common.exception.ExceptionHandlerUtils.createErrorResponse;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        return createErrorResponse(FORBIDDEN, e);
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenExpiredException(RefreshTokenExpiredException e) {
        return createErrorResponse(REFRESH_TOKEN_EXPIRED, e);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e) {
        return createErrorResponse(INVALID_REFRESH_TOKEN, e);
    }
}
