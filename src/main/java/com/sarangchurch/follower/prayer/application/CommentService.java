package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.prayer.application.dto.request.CommentCreate;
import com.sarangchurch.follower.prayer.domain.CardRepository;
import com.sarangchurch.follower.prayer.domain.Comment;
import com.sarangchurch.follower.prayer.domain.CommentRepository;
import com.sarangchurch.follower.prayer.domain.exception.CardNotFoundException;
import com.sarangchurch.follower.prayer.domain.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    public void createComment(Long memberId, Long cardId, CommentCreate request) {
        if (!cardRepository.existsById(cardId)) {
            throw new CardNotFoundException();
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .memberId(memberId)
                .cardId(cardId)
                .deleted(false)
                .build();

        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(CommentNotFoundException::new);
            comment.toParent(parent);
        }

        commentRepository.save(comment);
    }
}
