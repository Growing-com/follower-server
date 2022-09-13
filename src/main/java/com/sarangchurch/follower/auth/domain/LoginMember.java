package com.sarangchurch.follower.auth.domain;

import com.sarangchurch.follower.auth.domain.exception.ForbiddenException;
import com.sarangchurch.follower.member.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.singletonList;

public class LoginMember extends User {
    private final Member member;

    public LoginMember(Member member) {
        super(member.getUsername(), member.getPassword(), singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRoleName())));
        this.member = member;
    }

    public void validateAppRole() {
        if (RoleType.ADMIN.matchRole(getRoleType())) {
            throw new ForbiddenException();
        }
    }

    public void validateWebRole() {
        if (!RoleType.ADMIN.matchRole(getRoleType())) {
            throw new ForbiddenException();
        }
    }

    private RoleType getRoleType() {
        return RoleType.valueOf(getRoleName());
    }

    public String getRoleName() {
        return member.getRoleName();
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return member.getId();
    }
}
