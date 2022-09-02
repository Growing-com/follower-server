package com.sarangchurch.follower.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @EntityGraph(attributePaths = "role", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Member> findByUsername(String username);
}
