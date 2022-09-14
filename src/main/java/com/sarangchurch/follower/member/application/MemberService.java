package com.sarangchurch.follower.member.application;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRepository;
import com.sarangchurch.follower.member.domain.exception.IllegalMemberNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sarangchurch.follower.member.domain.exception.IllegalMemberNameException.*;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long save(Member member) {
        if (memberRepository.existsByName(member.getName())) {
            throw new IllegalMemberNameException(DUPLICATE_NAME);
        }
        memberRepository.save(member);
        return member.getId();
    }
}
