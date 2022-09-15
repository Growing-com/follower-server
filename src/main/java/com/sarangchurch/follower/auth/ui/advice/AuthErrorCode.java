package com.sarangchurch.follower.auth.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
    FORBIDDEN(HttpStatus.FORBIDDEN),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(HttpStatus.FORBIDDEN),
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN)
    ;

    private final HttpStatus httpStatus;

    AuthErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public int getStatus() {
        return httpStatus.value();
    }

    @Override
    public String getCode() {
        return name();
    }
}
