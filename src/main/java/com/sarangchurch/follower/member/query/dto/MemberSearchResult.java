package com.sarangchurch.follower.member.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarangchurch.follower.member.command.domain.model.Gender;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class MemberSearchResult {
    private final Long memberId;
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    private final Gender gender;
}
