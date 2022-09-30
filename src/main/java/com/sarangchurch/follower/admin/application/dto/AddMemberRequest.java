package com.sarangchurch.follower.admin.application.dto;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.common.EntitySupplier;
import com.sarangchurch.follower.member.domain.Gender;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AddMemberRequest implements EntitySupplier<Member> {
    @NotBlank
    @Size(min = 5, max = 20)
    private String username;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    @NotBlank
    @Size(min = 2, max = 20)
    private String name;
    @Past
    private LocalDate birthDate;
    @NotNull
    private Boolean earlyBorn;
    @NotNull
    private Gender gender;
    @NotNull
    private RoleType role;

    @Builder
    public AddMemberRequest(String username, String password, String name, LocalDate birthDate, Boolean earlyBorn, Gender gender, RoleType role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.earlyBorn = earlyBorn;
        this.gender = gender;
        this.role = role;
    }

    @Override
    public Member toEntity() {
        return Member.builder()
                .username(getUsername())
                .name(getName())
                .birthDate(getBirthDate())
                .earlyBorn(getEarlyBorn())
                .gender(getGender())
                .role(MemberRole.of(getRole()))
                .build();
    }
}
