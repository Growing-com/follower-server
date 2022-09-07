package com.sarangchurch.follower.member.ui;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.AuthMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/member")
public class TestController {

    @GetMapping("/checkAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TestResponse testAdmin(@AuthMember Member member) {
        return new TestResponse("WELCOME " + member.getUsername() + ". You hava ADMIN ROLE");
    }

    @GetMapping("/checkName")
    public TestResponse testMember(@AuthMember Member member) {
        if (member == null) {
            return new TestResponse("You are a GUEST user");
        }
        return new TestResponse("WELCOME " + member.getUsername());
    }

    static class TestResponse {
        private final String message;

        public TestResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
