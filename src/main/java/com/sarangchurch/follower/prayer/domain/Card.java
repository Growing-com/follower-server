package com.sarangchurch.follower.prayer.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Card {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
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

    public void setPrayers(List<Long> prayerIds) {
        cardPrayers.setPrayers(prayerIds);
    }

    public Long getId() {
        return id;
    }
}
