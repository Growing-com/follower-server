package com.sarangchurch.follower.aceeptance;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.department.domain.Department;
import com.sarangchurch.follower.department.domain.DepartmentRepository;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRepository;
import com.sarangchurch.follower.member.domain.MemberRole;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.sarangchurch.follower.auth.domain.RoleType.ADMIN;
import static com.sarangchurch.follower.auth.domain.RoleType.MANAGER;
import static com.sarangchurch.follower.member.domain.Gender.MALE;

@Component
@Profile("test")
public class DataLoader {
    public static final String TEST_ADMIN = "admin";
    public static final String TEST_MANAGER = "manager";
    public static final String TEST_PASSWORD = "password";

    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(MemberRepository memberRepository, DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void loadData() {
        Department department = createDepartment("대학 8부 Follow 공동체", "이순종 목사");

        departmentRepository.save(department);
        memberRepository.save(createMember(TEST_ADMIN, "이순종", ADMIN, department));
        memberRepository.save(createMember(TEST_MANAGER, "이종민", MANAGER, department));
    }

    private Department createDepartment(String name, String ministerName) {
        return Department.builder()
                .name(name)
                .ministerName(ministerName)
                .ministerPhone("010-1234-1234")
                .build();
    }

    private Member createMember(
            String username,
            String name,
            RoleType roleType,
            Department department
    ) {
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .name(name)
                .birthDate(LocalDate.of(1991, 11, 1))
                .earlyBorn(false)
                .gender(MALE)
                .role(MemberRole.of(roleType))
                .departmentId(department.getId())
                .build();
    }
}
