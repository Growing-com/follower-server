package com.sarangchurch.follower.member.command.application;

import com.sarangchurch.follower.member.command.application.dto.RegisterRequest;
import com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException.DUPLICATE_USERNAME;

@Service
@RequiredArgsConstructor
public class MemberRegisterService {

    private final MemberRepository memberRepository;
    private final MemberAssigner memberAssigner;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        // TODO: 미인증 -> SMS 보내기 -> SMS 확인 -> 인증
        validateDuplicateUsername(request);
        Member member = request.toEntity();
        member.changePassword(passwordEncoder.encode(request.getPassword().toLowerCase()));
        memberAssigner.assign(member, request.getTeamCode());
        memberRepository.save(member);
    }

    private void validateDuplicateUsername(RegisterRequest request) {
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new IllegalMemberException(DUPLICATE_USERNAME);
        }
    }
}
