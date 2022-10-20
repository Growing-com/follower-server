package com.sarangchurch.follower.prayer.query.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarangchurch.follower.member.command.domain.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PrayerDetails {
    private final Long cardId;
    @JsonIgnore
    private final LocalDateTime cardUpdatedAt;
    private final Long seq;
    private final String content;
    private final boolean response;
    @JsonIgnore
    private final Long memberId;
    @JsonIgnore
    private final String memberName;
    @JsonIgnore
    private final Gender gender;
    @JsonIgnore
    private final LocalDate birthDate;
}
