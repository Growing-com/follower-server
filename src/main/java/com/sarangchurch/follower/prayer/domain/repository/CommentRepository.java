package com.sarangchurch.follower.prayer.domain.repository;

import com.sarangchurch.follower.prayer.domain.model.Comment;

import java.util.Optional;

public interface CommentRepository {
    <S extends Comment> S save(S entity);

    Optional<Comment> findById(Long id);
}
