package com.sarangchurch.follower.department.command.domain.exception;

public class InactiveSeasonException extends IllegalStateException {
    public static final String DEFAULT_MESSAGE = "비활성화된 시즌입니다.";

    public InactiveSeasonException() {
        super(DEFAULT_MESSAGE);
    }
}
