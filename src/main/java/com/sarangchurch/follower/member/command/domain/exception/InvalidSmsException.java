package com.sarangchurch.follower.member.command.domain.exception;

public class InvalidSmsException extends IllegalArgumentException {

    public static final String EXPIRED_SMS = "유효하지 않은 인증번호입니다.";
    public static final String WRONG_CODE = "인증번호가 일치하지 않습니다.";
    public static final String NOT_VERIFIED = "확인되지 않은 인증번호입니다.";

    public InvalidSmsException(String s) {
        super(s);
    }
}
