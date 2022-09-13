package com.sarangchurch.follower.member.domain;

import com.sarangchurch.follower.auth.domain.RoleType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
        name = "member_role",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType name;

    protected MemberRole() {
    }

    public MemberRole(RoleType name) {
        this.name = name;
    }

    RoleType getRoleType() {
        return name;
    }
}
