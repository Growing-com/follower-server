package com.sarangchurch.follower.prayer.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ChildCommentDetails {
    private final Long commentId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime createdAt;
    private final Long cardId;
    private final Long parentId;
    private final String content;
    private final MemberDetails member;
}

