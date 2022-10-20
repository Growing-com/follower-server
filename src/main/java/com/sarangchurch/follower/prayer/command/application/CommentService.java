package com.sarangchurch.follower.prayer.command.application;

import com.sarangchurch.follower.prayer.command.application.dto.request.CommentCreate;
import com.sarangchurch.follower.prayer.command.domain.model.Comment;
import com.sarangchurch.follower.prayer.command.domain.repository.CommentRepository;
import com.sarangchurch.follower.prayer.command.domain.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    public void createComment(Long memberId, Long cardId, CommentCreate request) {
        if (!cardRepository.existsById(cardId)) {
            throw new EntityNotFoundException();
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .memberId(memberId)
                .cardId(cardId)
                .deleted(false)
                .build();

        if (request.getParentId() != null) {
            Comment parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(EntityNotFoundException::new);
            comment.toParent(parent);
        } else {
            comment.toParent(comment);
        }

        commentRepository.save(comment);
    }
}
