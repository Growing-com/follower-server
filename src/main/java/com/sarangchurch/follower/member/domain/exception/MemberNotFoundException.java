package com.sarangchurch.follower.member.domain.exception;

import javax.persistence.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {

    public static final String DEFAULT_MESSAGE = "존재하지 않는 사용자입니다.";

    public MemberNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
