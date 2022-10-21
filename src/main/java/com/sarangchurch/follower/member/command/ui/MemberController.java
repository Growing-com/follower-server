package com.sarangchurch.follower.member.command.ui;

import com.sarangchurch.follower.member.command.application.MemberRegisterService;
import com.sarangchurch.follower.member.command.application.dto.RegisterRequest;
import com.sarangchurch.follower.member.command.application.dto.SmsRequest;
import com.sarangchurch.follower.member.command.application.dto.SmsValidateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberRegisterService memberRegisterService;

    @PostMapping
    public void register(@RequestBody @Valid RegisterRequest request) {
        memberRegisterService.register(request);
    }

    @PostMapping("/requestSms")
    public void requestSms(@RequestBody @Valid SmsRequest request) {
        memberRegisterService.requestSms(request);
    }

    @PostMapping("/verifySms")
    public void validateSms(@RequestBody @Valid SmsValidateRequest request) {
        memberRegisterService.verifySms(request);
    }
}
