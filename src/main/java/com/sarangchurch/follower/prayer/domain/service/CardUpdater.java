package com.sarangchurch.follower.prayer.domain.service;

import com.sarangchurch.follower.prayer.domain.model.Card;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CardUpdater {

    private final PrayerRepository prayerRepository;

    public List<Prayer> update(Card card, List<Prayer> newPrayers) {
        prayerRepository.deleteByInitialCardId(card.getId());
        return prayerRepository.saveAll(newPrayers);
    }
}
