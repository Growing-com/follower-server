package com.sarangchurch.follower.prayer.domain.model;

import com.sarangchurch.follower.common.jpa.BaseEntity;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Card extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Version
    private long version;

    private Long memberId;
    private Long departmentId;
    @Embedded
    private Week week;
    @Embedded
    private CardPrayers cardPrayers = new CardPrayers();

    @Builder
    public Card(Long memberId, Long departmentId, Week week) {
        this.memberId = memberId;
        this.departmentId = departmentId;
        this.week = week;
    }

    public Prayer prayer(String content) {
        return Prayer.builder()
                .content(content)
                .responded(false)
                .memberId(memberId)
                .initialCardId(id)
                .build();
    }

    public void updatePrayers(List<Long> prayerIds) {
        cardPrayers.update(this, prayerIds);
    }

    public Long getId() {
        return id;
    }
}
