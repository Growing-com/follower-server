package com.sarangchurch.follower.department.command.domain.exception;

public class IllegalTeamCodeException extends IllegalArgumentException {
    public static final String DEFAULT_MESSAGE = "가입 코드가 일치하지 않습니다.";

    public IllegalTeamCodeException() {
        super(DEFAULT_MESSAGE);
    }
}
