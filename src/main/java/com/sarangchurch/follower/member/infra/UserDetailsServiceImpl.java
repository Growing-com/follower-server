package com.sarangchurch.follower.member.infra;

import com.sarangchurch.follower.auth.domain.LoginMember;
import com.sarangchurch.follower.auth.domain.TokenUserLoader;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, TokenUserLoader {

    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with id: %s", username)));

        return toLoginMember(member);
    }

    @Override
    public UserDetails loadUserByUserId(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user with sequence: %d", id)));

        return toLoginMember(member);
    }

    private LoginMember toLoginMember(Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .earlyBorn(member.isEarlyBorn())
                .gender(member.getGender())
                .role(member.getRole())
                .departmentId(member.getDepartmentId())
                .build();
    }
}
