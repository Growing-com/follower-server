package com.sarangchurch.follower.member.command.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {
    ILLEGAL_MEMBER(HttpStatus.BAD_REQUEST)
    ;

    private final HttpStatus httpStatus;

    MemberErrorCode(HttpStatus httpStatus) {
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
