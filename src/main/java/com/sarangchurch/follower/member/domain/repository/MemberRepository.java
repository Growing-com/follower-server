package com.sarangchurch.follower.member.domain.repository;

import com.sarangchurch.follower.member.domain.model.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUsername(String username);

    Optional<Member> findById(Long id);

    boolean existsByName(String name);

    boolean existsByUsername(String username);
}
