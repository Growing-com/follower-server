package com.sarangchurch.follower.prayer.domain;

import java.util.Optional;

public interface CommentRepository {
    <S extends Comment> S save(S entity);

    Optional<Comment> findById(Long id);
}
