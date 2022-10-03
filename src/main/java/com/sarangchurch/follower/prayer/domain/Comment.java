package com.sarangchurch.follower.prayer.domain;

import com.sarangchurch.follower.common.jpa.BaseEntity;
import com.sarangchurch.follower.prayer.domain.exception.CommentCreateException;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static com.sarangchurch.follower.prayer.domain.exception.CommentCreateException.ALREADY_HAS_PARENT;
import static com.sarangchurch.follower.prayer.domain.exception.CommentCreateException.IS_NOT_TOP;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
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

    @OneToMany(mappedBy = "parent", fetch = EAGER, cascade = ALL, orphanRemoval = true)
    @OrderBy("createDate asc")
    private List<Comment> children = new ArrayList<>();
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
        if (parent.hasParent()) {
            throw new CommentCreateException(IS_NOT_TOP);
        }
        if (hasParent()) {
            throw new CommentCreateException(ALREADY_HAS_PARENT);
        }
        this.parent = parent;
        parent.children.add(this);
    }

    private boolean hasParent() {
        return parent != null;
    }
}
