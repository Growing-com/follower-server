package com.sarangchurch.follower.prayer.ui.advice;

import com.sarangchurch.follower.common.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PrayerErrorCode implements ErrorCode {
    CANT_LINK_PRAYER(HttpStatus.BAD_REQUEST),
    DUPLICATE_PRAYER(HttpStatus.BAD_REQUEST),
    ILLEGAL_COMMENT(HttpStatus.BAD_REQUEST)
    ;

    private final HttpStatus httpStatus;

    PrayerErrorCode(HttpStatus httpStatus) {
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
