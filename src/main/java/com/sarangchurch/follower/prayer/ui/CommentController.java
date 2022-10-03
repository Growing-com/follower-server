package com.sarangchurch.follower.prayer.ui;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.ui.AuthMember;
import com.sarangchurch.follower.prayer.application.CommentService;
import com.sarangchurch.follower.prayer.application.dto.request.CommentCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/prayers/cards/{cardId}/comments")
    public void createComment(
            @AuthMember Member member,
            @PathVariable Long cardId,
            @RequestBody @Valid CommentCreate request
    ) {
        commentService.createComment(member.getId(), cardId, request);
    }

}
