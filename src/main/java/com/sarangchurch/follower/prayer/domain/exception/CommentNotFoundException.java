package com.sarangchurch.follower.prayer.domain.exception;

import javax.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {
    public static final String DEFAULT_MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
