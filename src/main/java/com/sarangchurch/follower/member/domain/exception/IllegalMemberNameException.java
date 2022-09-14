package com.sarangchurch.follower.member.domain.exception;

public class IllegalMemberNameException extends IllegalArgumentException {
    public static final String DUPLICATE_NAME = "동일한 이름의 유저가 있습니다";

    public IllegalMemberNameException(String message) {
        super(message);
    }
}
