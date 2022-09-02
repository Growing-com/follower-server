package com.sarangchurch.follower.member.domain;

import com.sarangchurch.follower.auth.domain.RoleType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType name;

    protected MemberRole() {
    }

    public MemberRole(RoleType name) {
        this.name = name;
    }

    String getName() {
        return name.name();
    }
}
