package com.sarangchurch.follower.member.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SmsRequest {
    @NotBlank(message = "번호를 입력해주세요.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "올바르지 않은 번호입니다.")
    private String phoneNumber;
}
