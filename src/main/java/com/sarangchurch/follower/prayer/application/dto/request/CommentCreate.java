package com.sarangchurch.follower.prayer.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CommentCreate {

    private Long parentId;

    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(min = 1, max = 200, message = "댓글은 최대 200글자까지 작성할 수 있습니다.")
    private String content;
}
