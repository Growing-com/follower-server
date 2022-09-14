package com.sarangchurch.follower.member.domain;

import com.sarangchurch.follower.auth.domain.RoleType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class MemberRole {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    public static MemberRole of(RoleType roleType) {
        return new MemberRole(roleType);
    }

    protected MemberRole() {
    }

    private MemberRole(RoleType role) {
        this.role = role;
    }

    RoleType getRole() {
        return role;
    }
}
