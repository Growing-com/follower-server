package com.sarangchurch.follower.common.exception;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    DB_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST),
    UNREADABLE_INPUT(HttpStatus.BAD_REQUEST),
    INVALID_INPUT(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    GlobalErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public int getStatus() {
        return httpStatus.value();
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
