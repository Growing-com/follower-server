package com.sarangchurch.follower.prayer.domain.exception;

public class CommentCreateException extends RuntimeException {
    public static final String IS_NOT_TOP = "대댓글에 대댓글을 달 수 없습니다.";
    public static final String CANT_CHANGE_PARENT = "대댓글의 부모 댓글을 변경할 수 없습니다.";

    public CommentCreateException(String message) {
        super(message);
    }
}
