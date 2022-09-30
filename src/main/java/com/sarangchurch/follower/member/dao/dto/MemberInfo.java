package com.sarangchurch.follower.member.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarangchurch.follower.auth.domain.RoleType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberInfo {

    private final String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;
    private final String departmentName;
    private final Long departmentId;
    private final RoleType roleType;

    public MemberInfo(String name, LocalDate birthDate, String departmentName, Long departmentId, RoleType roleType) {
        this.name = name;
        this.birthDate = birthDate;
        this.departmentName = departmentName;
        this.departmentId = departmentId;
        this.roleType = roleType;
    }
}
