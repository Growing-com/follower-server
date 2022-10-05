package com.sarangchurch.follower.member.domain.model;

import com.sarangchurch.follower.auth.domain.model.RoleType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class MemberRole {
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public static MemberRole of(RoleType roleType) {
        return new MemberRole(roleType);
    }

    protected MemberRole() {
    }

    private MemberRole(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }
}
