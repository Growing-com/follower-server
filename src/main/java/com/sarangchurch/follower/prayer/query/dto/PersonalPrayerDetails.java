package com.sarangchurch.follower.prayer.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PersonalPrayerDetails {
    private final Long cardId;
    private final Long seq;
    private final Long prayerId;
    private final String content;
    private final boolean response;
}
