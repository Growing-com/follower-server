package com.sarangchurch.follower.member.command.domain.exception;

public class IllegalMemberException extends IllegalArgumentException {
    public static final String DUPLICATE_USERNAME = "이미 사용중인 아이디입니다";
    public static final String DUPLICATE_NAME = "동일한 이름의 유저가 있습니다";

    public IllegalMemberException(String message) {
        super(message);
    }
}
