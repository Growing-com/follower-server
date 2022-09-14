package com.sarangchurch.follower.auth.domain.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    private static final String DEFAULT_MSG = "만료된 Refresh Token 입니다.";

    public RefreshTokenExpiredException() {
        super(DEFAULT_MSG);
    }
}
