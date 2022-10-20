package com.sarangchurch.follower.prayer.command.domain.model;

import com.sarangchurch.follower.auth.domain.exception.ForbiddenException;
import com.sarangchurch.follower.prayer.command.domain.exception.CantLinkPrayerException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String content;
    private boolean responded;
    private Long memberId;
    private Long initialCardId;

    @Builder
    public Prayer(String content, boolean responded, Long memberId, Long initialCardId) {
        this.content = content;
        this.responded = responded;
        this.memberId = memberId;
        this.initialCardId = initialCardId;
    }

    public void validateLinkable(Long memberId, Long cardId) {
        if (!this.memberId.equals(memberId)) {
            throw new CantLinkPrayerException(CantLinkPrayerException.NOT_MY_PRAYER);
        }

        if (this.initialCardId.equals(cardId)) {
            throw new CantLinkPrayerException(CantLinkPrayerException.SAME_INITIAL_CARD);
        }

        if (responded) {
            throw new CantLinkPrayerException(CantLinkPrayerException.ALREADY_RESPONDED);
        }
    }

    public void respond(Long memberId) {
        validateOwner(memberId);
        responded = true;
    }

    private void validateOwner(Long memberId) {
        if (!this.memberId.equals(memberId)) {
            throw new ForbiddenException();
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prayer prayer = (Prayer) o;
        return Objects.equals(getId(), prayer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
