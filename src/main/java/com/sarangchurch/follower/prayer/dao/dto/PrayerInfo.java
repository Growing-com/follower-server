package com.sarangchurch.follower.prayer.dao.dto;

import com.sarangchurch.follower.member.domain.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PrayerInfo {
    private final Long cardId;
    private final Long seq;
    private final String content;
    private final boolean response;
    private final Long memberId;
    private final String memberName;
    private final Gender gender;
    private final LocalDate birthDate;
}
