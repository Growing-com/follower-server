package com.sarangchurch.follower.admin.domain.exception;

public class ForbiddenDepartmentException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "작업 권한이 없는 부서입니다.";

    public ForbiddenDepartmentException() {
        super(DEFAULT_MESSAGE);
    }
}
