package com.sarangchurch.follower.prayer.dao.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MyPrayerDetails {
    private final Long cardId;
    @JsonIgnore
    private final LocalDateTime cardUpdateTime;
    private final Long seq;
    private final String content;
    private final boolean response;
}