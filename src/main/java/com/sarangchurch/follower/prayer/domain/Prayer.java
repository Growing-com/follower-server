package com.sarangchurch.follower.prayer.domain;

import com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException;
import com.sarangchurch.follower.prayer.domain.exception.IllegalPrayerOperationException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import static com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException.*;
import static com.sarangchurch.follower.prayer.domain.exception.IllegalPrayerOperationException.*;

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
    public Prayer(String content, boolean responded) {
        this.content = content;
        this.responded = responded;
    }

    public Prayer toMember(Long memberId) {
        if (this.memberId != null) {
            throw new IllegalPrayerOperationException(CANT_CHANGE_MEMBER);
        }
        this.memberId = memberId;
        return this;
    }

    public Prayer toCard(Long initialCardId) {
        if (this.initialCardId != null) {
            throw new IllegalPrayerOperationException(CANT_CHANGE_CARD);
        }
        this.initialCardId = initialCardId;
        return this;
    }

    public void validateLinkable(Long memberId, Long cardId) {
        if (!this.memberId.equals(memberId)) {
            throw new CantLinkPrayerException(NOT_MY_PRAYER);
        }

        if (this.initialCardId.equals(cardId)) {
            throw new CantLinkPrayerException(SAME_INITIAL_CARD);
        }

        if (responded) {
            throw new CantLinkPrayerException(ALREADY_RESPONDED);
        }
    }

    public Long getId() {
        return id;
    }
}
