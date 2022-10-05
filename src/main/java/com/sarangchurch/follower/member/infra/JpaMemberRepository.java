package com.sarangchurch.follower.member.infra;

import com.sarangchurch.follower.member.domain.model.Member;
import com.sarangchurch.follower.member.domain.repository.MemberRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    @Override
    @EntityGraph(attributePaths = "role", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Member> findByUsername(String username);
}
