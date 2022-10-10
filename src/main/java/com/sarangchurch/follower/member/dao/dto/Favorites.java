package com.sarangchurch.follower.member.dao.dto;

import com.sarangchurch.follower.member.domain.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class Favorites {
    private final Long memberId;
    private final String name;
    private final LocalDate birthDate;
    private final Gender gender;
}
