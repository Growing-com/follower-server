package com.sarangchurch.follower.prayer.domain.exception;

import javax.persistence.EntityNotFoundException;

public class CardNotFoundException extends EntityNotFoundException {
    public static final String DEFAULT_MESSAGE = "존재하지 않는 카드입니다.";

    public CardNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
