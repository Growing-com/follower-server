package com.sarangchurch.follower.auth.domain.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    private static final String DEFAULT_MSG = "존재하지 않는 Refresh Token 입니다.";

    public RefreshTokenNotFoundException() {
        super(DEFAULT_MSG);
    }
}
