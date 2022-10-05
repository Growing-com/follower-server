package com.sarangchurch.follower.prayer.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPrayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "card_id")
    private Card card;
    private Long prayerId;

    CardPrayer(Card card, Long prayerId) {
        this.card = card;
        this.prayerId = prayerId;
    }
}
