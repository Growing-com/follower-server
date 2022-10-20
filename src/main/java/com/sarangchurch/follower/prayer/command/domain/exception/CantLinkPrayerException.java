package com.sarangchurch.follower.prayer.command.domain.exception;

public class CantLinkPrayerException extends RuntimeException {

    public static String NOT_MY_PRAYER = "다른 사용자의 기도를 연결할 수 없습니다.";
    public static String SAME_INITIAL_CARD = "같은 카드의 기도를 연결할 수 없습니다.";
    public static String ALREADY_RESPONDED = "응답 받은 기도를 연결할 수 없습니다.";

    public CantLinkPrayerException(String message) {
        super(message);
    }
}
