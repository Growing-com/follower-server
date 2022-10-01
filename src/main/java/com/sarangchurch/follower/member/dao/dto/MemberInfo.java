package com.sarangchurch.follower.member.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarangchurch.follower.auth.domain.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MemberInfo {

    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    private final String departmentName;
    private final Long departmentId;
    private final RoleType roleType;
}
