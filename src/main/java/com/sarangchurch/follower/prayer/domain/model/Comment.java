package com.sarangchurch.follower.prayer.domain.model;

import com.sarangchurch.follower.common.jpa.BaseEntity;
import com.sarangchurch.follower.prayer.domain.exception.CommentCreateException;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Objects;

import static com.sarangchurch.follower.prayer.domain.exception.CommentCreateException.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "comments")
@NoArgsConstructor(access = PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String content;
    private Long memberId;
    private Long cardId;
    private boolean deleted;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder
    public Comment(String content, Long memberId, Long cardId, boolean deleted) {
        this.content = content;
        this.memberId = memberId;
        this.cardId = cardId;
        this.deleted = deleted;
    }

    public void toParent(Comment parent) {
        validateParent(parent);
        this.parent = parent;
    }

    private void validateParent(Comment parent) {
        if (this.parent != null) {
            throw new CommentCreateException(CANT_CHANGE_PARENT);
        }
        if (this == parent) {
            return;
        }
        if (!parent.isTop()) {
            throw new CommentCreateException(IS_NOT_TOP);
        }
    }

    private boolean isTop() {
        return this.parent == this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
