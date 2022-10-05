package com.sarangchurch.follower.prayer.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarangchurch.follower.member.domain.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MemberDetails {
    private final Long id;
    private final String name;
    private final Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
}
