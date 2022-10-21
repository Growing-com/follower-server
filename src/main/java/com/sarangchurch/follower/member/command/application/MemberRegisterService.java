package com.sarangchurch.follower.member.command.application;

import com.sarangchurch.follower.common.events.Events;
import com.sarangchurch.follower.common.types.vo.PhoneNumber;
import com.sarangchurch.follower.member.command.application.dto.RegisterRequest;
import com.sarangchurch.follower.member.command.application.dto.SmsRequest;
import com.sarangchurch.follower.member.command.application.dto.SmsValidateRequest;
import com.sarangchurch.follower.member.command.domain.events.SmsSavedEvent;
import com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException;
import com.sarangchurch.follower.member.command.domain.exception.InvalidSmsException;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.domain.model.Sms;
import com.sarangchurch.follower.member.command.domain.repository.MemberRepository;
import com.sarangchurch.follower.member.command.domain.repository.SmsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException.DUPLICATE_PHONE;
import static com.sarangchurch.follower.member.command.domain.exception.IllegalMemberException.DUPLICATE_USERNAME;
import static com.sarangchurch.follower.member.command.domain.exception.InvalidSmsException.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberRegisterService {

    private final SmsRepository smsRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberAssigner memberAssigner;
    private final MemberRepository memberRepository;

    @Transactional
    public void register(RegisterRequest request) {
        validateRequest(request);

        Member member = request.toEntity();
        member.changePassword(passwordEncoder.encode(request.getPassword().trim().toLowerCase()));
        memberAssigner.assign(member, request.getTeamCode());
        memberRepository.save(member);
    }

    private void validateRequest(RegisterRequest request) {
        validatePasswordCheck(request);
        validateDuplicate(request);
        validatePhoneNumber(request);
    }

    private void validatePasswordCheck(RegisterRequest request) {
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }
    }

    private void validateDuplicate(RegisterRequest request) {
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new IllegalMemberException(DUPLICATE_USERNAME);
        }

        PhoneNumber phoneNumber = new PhoneNumber(request.getPhoneNumber());
        if (memberRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalMemberException(DUPLICATE_PHONE);
        }
    }

    private void validatePhoneNumber(RegisterRequest request) {
        Sms sms = smsRepository.findById(request.getPhoneNumber())
                .orElseThrow();

        if (!sms.isVerified()) {
            throw new InvalidSmsException(NOT_VERIFIED);
        }
    }

    @Transactional
    public void requestSms(SmsRequest request) {
        Sms sms = new Sms(new PhoneNumber(request.getPhoneNumber()));
        smsRepository.save(sms);

        Events.raise(new SmsSavedEvent(sms.getPhoneNumber(), sms.getCode()));
    }

    @Transactional
    public void verifySms(SmsValidateRequest request) {
        String phoneNumber = request.getPhoneNumber();
        Sms sms = smsRepository.findById(phoneNumber)
                .orElseThrow(() -> new InvalidSmsException(EXPIRED_SMS));
        log.info("validating sms: {}", sms);

        if (sms.matchCode(request.getCode())) {
            throw new InvalidSmsException(WRONG_CODE);
        }

        sms.verify();
        smsRepository.save(sms);
    }
}
