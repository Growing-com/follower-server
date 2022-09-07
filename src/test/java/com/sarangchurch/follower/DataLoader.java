package com.sarangchurch.follower;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRepository;
import com.sarangchurch.follower.member.domain.MemberRole;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class DataLoader {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void loadData() {
        memberRepository.save(new Member("admin", passwordEncoder.encode("password"), new MemberRole(RoleType.ADMIN)));
        memberRepository.save(new Member("leader", passwordEncoder.encode("password"), new MemberRole(RoleType.LEADER)));
        memberRepository.save(new Member("member", passwordEncoder.encode("password"), new MemberRole(RoleType.MEMBER)));
    }
}
