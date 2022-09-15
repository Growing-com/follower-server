package com.sarangchurch.follower.auth.domain.exception;

public class UnauthorizedException extends RuntimeException {
    private static final String DEFAULT_MSG = "인증되지 않은 사용자입니다.";

    public UnauthorizedException() {
        super(DEFAULT_MSG);
    }
}
