package com.sarangchurch.follower.prayer.domain.exception;

public class IllegalPrayerOperationException extends RuntimeException {

    public static String CANT_CHANGE_MEMBER = "기도 소유자를 변경할 수 없습니다.";
    public static String CANT_CHANGE_CARD = "최초 기도 카드를 변경할 수 없습니다.";

    public IllegalPrayerOperationException(String message) {
        super(message);
    }
}
