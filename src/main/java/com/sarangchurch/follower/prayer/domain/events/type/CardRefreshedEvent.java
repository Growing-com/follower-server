package com.sarangchurch.follower.prayer.domain.events.type;

import com.sarangchurch.follower.prayer.domain.model.Card;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import lombok.Getter;

import java.util.List;

@Getter
public class CardRefreshedEvent {
    private final Card card;
    private final List<Prayer> newPrayers;

    public CardRefreshedEvent(Card card, List<Prayer> newPrayers) {
        this.card = card;
        this.newPrayers = newPrayers;
    }
}
