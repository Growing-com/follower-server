package com.sarangchurch.follower.common.exception;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    DATABASE_FAILURE(HttpStatus.BAD_REQUEST),
    CONVERSION_FAILURE(HttpStatus.BAD_REQUEST),
    VALIDATION_FAILURE(HttpStatus.BAD_REQUEST);

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
