package com.sarangchurch.follower.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
public class BulkUpdateFavorite {
    @NotNull(message = "즐겨찾기에 추가할 멤버들을 입력해주세요.")
    private List<Long> add;
    @NotNull(message = "즐겨찾기에서 지울 멤버들을 입력해주세요.")
    private List<Long> remove;

    public BulkUpdateFavorite(List<Long> add, List<Long> remove) {
        this.add = add;
        this.remove = remove;
    }
}
