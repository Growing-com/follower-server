package com.sarangchurch.follower.prayer.domain.exception;

public class DuplicatePrayerException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "기도 카드에 중복된 기도가 있습니다.";

    public DuplicatePrayerException() {
        super(DEFAULT_MESSAGE);
    }
}
