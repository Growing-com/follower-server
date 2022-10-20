package com.sarangchurch.follower.prayer.command.infra;

import com.sarangchurch.follower.prayer.command.domain.repository.CommentRepository;
import com.sarangchurch.follower.prayer.command.domain.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long>, CommentRepository {
}
