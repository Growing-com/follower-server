package com.sarangchurch.follower.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RefreshTokenNotFoundException extends RuntimeException {
    private static final String DEFAULT_MSG = "존재하지 않는 Refresh Token 입니다.";

    public RefreshTokenNotFoundException() {
        super(DEFAULT_MSG);
    }
}
