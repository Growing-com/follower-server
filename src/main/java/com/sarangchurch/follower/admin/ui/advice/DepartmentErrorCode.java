package com.sarangchurch.follower.admin.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum DepartmentErrorCode implements ErrorCode {
    FORBIDDEN_DEPARTMENT(HttpStatus.FORBIDDEN),
    ;

    private final HttpStatus httpStatus;

    DepartmentErrorCode(HttpStatus httpStatus) {
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
