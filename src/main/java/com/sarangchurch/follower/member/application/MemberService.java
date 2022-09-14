package com.sarangchurch.follower.member.application;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRepository;
import com.sarangchurch.follower.member.domain.exception.IllegalMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sarangchurch.follower.member.domain.exception.IllegalMemberException.*;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long save(Member member) {
        validateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateMember(Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new IllegalMemberException(DUPLICATE_USERNAME);
        }

        if (memberRepository.existsByName(member.getName())) {
            throw new IllegalMemberException(DUPLICATE_NAME);
        }
    }
}
