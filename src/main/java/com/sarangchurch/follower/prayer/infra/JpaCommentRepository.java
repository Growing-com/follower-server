package com.sarangchurch.follower.prayer.infra;

import com.sarangchurch.follower.prayer.domain.model.Comment;
import com.sarangchurch.follower.prayer.domain.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long>, CommentRepository {
}
