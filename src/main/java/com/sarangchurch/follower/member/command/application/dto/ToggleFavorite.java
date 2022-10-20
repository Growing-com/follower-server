package com.sarangchurch.follower.member.command.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ToggleFavorite {
    @NotNull(message = "즐겨찾기에 추가 및 삭제할 사용자를 지정해주세요.")
    private Long memberId;

    public ToggleFavorite(Long memberId) {
        this.memberId = memberId;
    }
}
