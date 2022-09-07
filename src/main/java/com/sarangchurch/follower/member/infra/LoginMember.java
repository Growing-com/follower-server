package com.sarangchurch.follower.member.infra;

import com.sarangchurch.follower.member.domain.Member;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.singletonList;

public class LoginMember extends User {
    private final Member member;

    public LoginMember(Member member) {
        super(member.getUsername(), member.getPassword(), singletonList(new SimpleGrantedAuthority("ROLE_" + member.role())));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
