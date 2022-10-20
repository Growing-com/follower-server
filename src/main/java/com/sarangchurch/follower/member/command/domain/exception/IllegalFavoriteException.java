package com.sarangchurch.follower.member.command.domain.exception;

public class IllegalFavoriteException extends IllegalArgumentException {
    public static final String DEFAULT_MESSAGE = "자기 자신을 즐겨찾기 할 수 없습니다.";

    public IllegalFavoriteException() {
        super(DEFAULT_MESSAGE);
    }
}
