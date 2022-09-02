package com.sarangchurch.follower;

import com.sarangchurch.follower.auth.RoleType;
import com.sarangchurch.follower.member.Member;
import com.sarangchurch.follower.member.MemberRepository;
import com.sarangchurch.follower.member.MemberRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
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
