package com.sarangchurch.follower.prayer.dao.dto;

import com.sarangchurch.follower.member.domain.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PrayerInfo {
    private final Long cardId;
    private final Long seq;
    private final String content;
    private final boolean response;
    private final Long memberId;
    private final String memberName;
    private final Gender gender;
    private final LocalDate birthDate;

    public PrayerInfo(Long cardId, Long seq, String content, boolean response, Long memberId, String memberName, Gender gender, LocalDate birthDate) {
        this.cardId = cardId;
        this.seq = seq;
        this.content = content;
        this.response = response;
        this.memberId = memberId;
        this.memberName = memberName;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
