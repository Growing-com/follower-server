package com.sarangchurch.follower.member.command.application.dto;

import com.sarangchurch.follower.auth.domain.model.RoleType;
import com.sarangchurch.follower.common.types.marker.EntitySupplier;
import com.sarangchurch.follower.common.types.vo.PhoneNumber;
import com.sarangchurch.follower.member.command.domain.model.Gender;
import com.sarangchurch.follower.member.command.domain.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterRequest implements EntitySupplier<Member> {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "(?i)^(?=.*[a-z])[a-z0-9]{8,20}$",
            message = "영문, 숫자 8~20자")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*()._-]{8,20}$",
            message = "영문, 숫자, 특수문자 8~20자")
    private String password;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*()._-]{8,20}$",
            message = "영문, 숫자, 특수문자 8~20자")
    private String passwordCheck;
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름을 입력해주세요.")
    private String name;
    @Past(message = "올바르지 않은 생년월일입니다.")
    private LocalDate birthDate;
    @NotNull(message = "빠른년생 여부를 입력해주세요.")
    private Boolean earlyBorn;
    @NotNull(message = "성별을 입력해주세요.")
    private Gender gender;
    @NotBlank(message = "번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "올바르지 않은 번호입니다.")
    private String phoneNumber;
    @NotNull(message = "팀 코드를 입력해주세요.")
    @Size(min = 6, max = 6)
    private String teamCode;

    @Builder
    public RegisterRequest(String username, String password, String name, LocalDate birthDate, Boolean earlyBorn, Gender gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.earlyBorn = earlyBorn;
        this.gender = gender;
    }

    @Override
    public Member toEntity() {
        return Member.builder()
                .username(getUsername())
                .name(getName())
                .birthDate(getBirthDate())
                .earlyBorn(getEarlyBorn())
                .gender(getGender())
                .role(RoleType.MEMBER)
                .phoneNumber(new PhoneNumber(phoneNumber))
                .build();
    }
}
