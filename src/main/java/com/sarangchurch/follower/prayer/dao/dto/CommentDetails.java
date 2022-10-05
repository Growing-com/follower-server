package com.sarangchurch.follower.prayer.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentDetails {
    private final Long commentId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime createdAt;
    private final Long cardId;
    private final Long parentId;
    private final String content;
    private final MemberDetails member;
    private final List<ChildCommentDetails> children;
}

