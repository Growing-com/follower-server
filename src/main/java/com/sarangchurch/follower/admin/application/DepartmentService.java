package com.sarangchurch.follower.admin.application;

import com.sarangchurch.follower.admin.application.dto.AddMemberRequest;
import com.sarangchurch.follower.admin.domain.Admin;
import com.sarangchurch.follower.admin.domain.exception.ForbiddenDepartmentException;
import com.sarangchurch.follower.member.application.MemberService;
import com.sarangchurch.follower.member.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public DepartmentService(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long addMember(Admin admin, Long departmentId, AddMemberRequest request) {
        if (!admin.hasDepartmentAccess(departmentId)) {
            throw new ForbiddenDepartmentException();
        }

        Member member = request.toEntity();
        member.changePassword(passwordEncoder.encode(request.getPassword()));
        member.toDepartment(departmentId);
        return memberService.save(member);
    }
}
