package com.sarangchurch.follower.department.domain.exception;

public class DuplicateMemberException extends IllegalStateException {
    public static final String DEFAULT_MESSAGE = "이미 존재하는 팀원입니다.";

    public DuplicateMemberException() {
        super(DEFAULT_MESSAGE);
    }
}
