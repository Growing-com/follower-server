package com.sarangchurch.follower.auth.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {
    private static final String DEFAULT_MSG = "알맞은 권한이 없습니다.";

    public ForbiddenException() {
        super(DEFAULT_MSG);
    }
}
