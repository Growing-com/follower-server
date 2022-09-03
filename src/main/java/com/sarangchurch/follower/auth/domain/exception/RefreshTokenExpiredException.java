package com.sarangchurch.follower.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RefreshTokenExpiredException extends RuntimeException {
    private static final String DEFAULT_MSG = "만료된 Refresh Token 입니다.";

    public RefreshTokenExpiredException() {
        super(DEFAULT_MSG);
    }
}
