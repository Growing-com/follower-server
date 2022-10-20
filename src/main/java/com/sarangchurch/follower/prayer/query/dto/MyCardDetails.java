package com.sarangchurch.follower.prayer.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class MyCardDetails {
    private final Long cardId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime updatedAt;
    private final List<MyPrayerDetails> prayers;
}
