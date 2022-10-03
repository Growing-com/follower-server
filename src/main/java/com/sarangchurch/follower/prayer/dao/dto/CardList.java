package com.sarangchurch.follower.prayer.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarangchurch.follower.member.domain.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardList {
    private final Long cardId;
    @JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private final LocalDateTime updateTime;
    private final List<PrayerList> prayers;
    private final MemberDetails member;

    @Getter
    @RequiredArgsConstructor
    public static class PrayerList {
        private final Long cardId;
        @JsonIgnore
        private final LocalDateTime cardUpdateTime;
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

    @Getter
    @RequiredArgsConstructor
    public static class MemberDetails {
        private final Long id;
        private final String name;
        private final Gender gender;
        @JsonFormat(pattern = "yyyy-MM-dd")
        private final LocalDate birthDate;
    }
}
