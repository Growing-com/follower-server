package com.sarangchurch.follower.member.command.application;

import com.sarangchurch.follower.member.command.domain.model.Member;

@FunctionalInterface
public interface MemberAssigner {
    void assign(Member member, String teamCode);
}
