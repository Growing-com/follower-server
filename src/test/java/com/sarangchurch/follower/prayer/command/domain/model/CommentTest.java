package com.sarangchurch.follower.prayer.command.domain.model;

import com.sarangchurch.follower.prayer.command.domain.exception.CommentCreateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.prayer.command.domain.exception.CommentCreateException.CANT_CHANGE_PARENT;
import static com.sarangchurch.follower.prayer.command.domain.exception.CommentCreateException.IS_NOT_TOP;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentTest {

    @DisplayName("최상위 댓글은 자기 자신을 부모 댓글로 설정한다.")
    @Test
    void toParent() {
        Comment topComment = Comment.builder().build();

        assertThatNoException().isThrownBy(() -> topComment.toParent(topComment));
    }

    @DisplayName("대댓글은 상위 댓글을 부모 댓글로 설정한다.")
    @Test
    void toParent2() {
        Comment topComment = Comment.builder().build();
        Comment childComment = Comment.builder().build();

        topComment.toParent(topComment);

        assertThatNoException().isThrownBy(() -> childComment.toParent(topComment));
    }

    @DisplayName("부모 댓글은 한 번 설정하면 변경할 수 없다.")
    @Test
    void toParent_Exception() {
        Comment topComment = Comment.builder().build();
        Comment childComment = Comment.builder().build();

        topComment.toParent(topComment);
        childComment.toParent(topComment);

        assertThatThrownBy(() -> childComment.toParent(topComment))
                .isInstanceOf(CommentCreateException.class)
                .hasMessage(CANT_CHANGE_PARENT);
    }

    @DisplayName("대댓글의 부모 댓글은 최상위 댓글이어야 한다.")
    @Test
    void toParent_Exception2() {
        Comment comment1 = Comment.builder().build();
        Comment comment2 = Comment.builder().build();
        Comment comment3 = Comment.builder().build();

        comment3.toParent(comment3);
        comment2.toParent(comment3);

        assertThatThrownBy(() -> comment1.toParent(comment2))
                .isInstanceOf(CommentCreateException.class)
                .hasMessage(IS_NOT_TOP);
    }
}
