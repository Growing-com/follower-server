package com.sarangchurch.follower.prayer.query.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardDetails {
    private final Long cardId;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime updatedAt;
    private final List<PrayerDetails> prayers;
    private final MemberDetails member;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<CommentDetails> comments;
}
