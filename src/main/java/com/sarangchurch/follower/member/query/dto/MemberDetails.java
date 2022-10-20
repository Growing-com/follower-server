package com.sarangchurch.follower.member.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarangchurch.follower.auth.domain.model.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MemberDetails {
    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    private final String departmentName;
    private final Long departmentId;
    private final RoleType roleType;
}
