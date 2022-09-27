package com.sarangchurch.follower.prayer.domain;

import com.sarangchurch.follower.prayer.domain.exception.DuplicatePrayerException;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class CardPrayers {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id asc")
    @JoinColumn(name = "card_id")
    private List<CardPrayer> cardPrayers = new ArrayList<>();

    void setPrayers(List<Long> prayerIds) {
        cardPrayers.clear();
        cardPrayers.addAll(toCardPrayers(prayerIds));
    }

    private List<CardPrayer> toCardPrayers(List<Long> prayerIds) {
        validateDuplicate(prayerIds);
        return prayerIds.stream()
                .map(CardPrayer::new)
                .collect(Collectors.toList());
    }

    private void validateDuplicate(List<Long> prayerIds) {
        if (prayerIds.size() != new HashSet<>(prayerIds).size()) {
            throw new DuplicatePrayerException();
        }
    }
}
