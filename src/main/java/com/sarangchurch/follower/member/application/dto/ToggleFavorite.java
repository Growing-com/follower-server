package com.sarangchurch.follower.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ToggleFavorite {
    @NotNull(message = "즐겨찾기에 추가 및 삭제할 사용자를 지정해주세요.")
    private Long memberId;
}
