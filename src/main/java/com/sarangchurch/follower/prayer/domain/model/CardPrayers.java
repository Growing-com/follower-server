package com.sarangchurch.follower.prayer.domain.model;

import com.sarangchurch.follower.prayer.domain.exception.DuplicatePrayerException;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class CardPrayers {

    @OneToMany(mappedBy = "card", cascade = ALL, orphanRemoval = true, fetch = EAGER)
    @OrderBy("id asc")
    private List<CardPrayer> cardPrayers = new ArrayList<>();

    void update(Card card, List<Long> prayerIds) {
        cardPrayers.clear();
        cardPrayers.addAll(toCardPrayers(card, prayerIds));
    }

    private List<CardPrayer> toCardPrayers(Card card, List<Long> prayerIds) {
        validateDuplicate(prayerIds);
        return prayerIds.stream()
                .map(it -> new CardPrayer(card, it))
                .collect(Collectors.toList());
    }

    private void validateDuplicate(List<Long> prayerIds) {
        if (prayerIds.size() != new HashSet<>(prayerIds).size()) {
            throw new DuplicatePrayerException();
        }
    }
}
