package com.sarangchurch.follower.prayer.dao.dto;

import com.sarangchurch.follower.member.domain.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberInfo {
    private final Long id;
    private final String name;
    private final Gender gender;
    private final LocalDate birthDate;

    public MemberInfo(Long id, String name, Gender gender, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}
