package com.sarangchurch.follower.prayer.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MyCardDetails {
    private final Long cardId;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private final LocalDateTime updateTime;
    private final List<MyPrayerList> prayers;

    @Getter
    @RequiredArgsConstructor
    public static class MyPrayerList {
        private final Long cardId;
        @JsonIgnore
        private final LocalDateTime cardUpdateTime;
        private final Long seq;
        private final String content;
        private final boolean response;
    }
}
