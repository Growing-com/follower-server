package com.sarangchurch.follower.prayer.command.domain.repository;

import com.sarangchurch.follower.prayer.command.domain.model.Comment;

import java.util.Optional;

public interface CommentRepository {
    <S extends Comment> S save(S entity);

    Optional<Comment> findById(Long id);
}
