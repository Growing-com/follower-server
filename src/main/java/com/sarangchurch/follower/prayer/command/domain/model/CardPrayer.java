package com.sarangchurch.follower.prayer.command.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardPrayer that = (CardPrayer) o;
        return Objects.equals(card, that.card) && Objects.equals(prayerId, that.prayerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card, prayerId);
    }
}
