package com.sarangchurch.follower.prayer.domain.service;

import com.sarangchurch.follower.prayer.domain.model.Card;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardUpdater {

    private final PrayerRepository prayerRepository;

    public void update(Card card, List<Prayer> newPrayers) {
        prayerRepository.deleteByInitialCardId(card.getId());

        List<Long> newPrayerIds = prayerRepository.saveAll(newPrayers)
                .stream()
                .map(Prayer::getId)
                .collect(Collectors.toList());
        card.updatePrayers(newPrayerIds);
    }
}
