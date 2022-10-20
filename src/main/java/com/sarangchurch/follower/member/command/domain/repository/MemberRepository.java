package com.sarangchurch.follower.member.command.domain.repository;

import com.sarangchurch.follower.member.command.domain.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findByUsername(String username);

    Optional<Member> findById(Long id);

    long countByIdIn(List<Long> ids);

    boolean existsByName(String name);

    boolean existsByUsername(String username);
}
