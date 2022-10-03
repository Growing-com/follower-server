package com.sarangchurch.follower.prayer.infra;

import com.sarangchurch.follower.prayer.domain.Comment;
import com.sarangchurch.follower.prayer.domain.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<Comment, Long>, CommentRepository {
}
