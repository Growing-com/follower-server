package com.sarangchurch.follower.member.command.ui;

import com.sarangchurch.follower.member.command.application.MemberRegisterService;
import com.sarangchurch.follower.member.command.application.dto.RegisterRequest;
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
        validatePasswordCheck(request);
        memberRegisterService.register(request);
    }

    private void validatePasswordCheck(RegisterRequest request) {
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }
    }
}
