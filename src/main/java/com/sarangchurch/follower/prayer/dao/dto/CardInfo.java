package com.sarangchurch.follower.prayer.dao.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardInfo {
    private final Long cardId;
    private final List<PrayerInfo> prayers;
    private final MemberInfo member;
}
