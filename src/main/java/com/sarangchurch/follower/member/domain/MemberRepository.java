package com.sarangchurch.follower.member.domain;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUsername(String username);

    Optional<Member> findById(Long id);
}
