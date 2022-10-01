package com.sarangchurch.follower.prayer.dao.dto;

import com.sarangchurch.follower.member.domain.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MemberInfo {
    private final Long id;
    private final String name;
    private final Gender gender;
    private final LocalDate birthDate;
}
