package com.sarangchurch.follower.prayer.domain.events.handler;

import com.sarangchurch.follower.prayer.domain.events.type.CardRefreshedEvent;
import com.sarangchurch.follower.prayer.domain.model.Card;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardRefreshedHandler {
    private final PrayerRepository prayerRepository;

    @EventListener(CardRefreshedEvent.class)
    public void handleCardRefreshed(CardRefreshedEvent evt) {
        Card card = evt.getCard();
        List<Prayer> newPrayers = evt.getNewPrayers();

        prayerRepository.deleteByInitialCardId(card.getId());
        List<Long> newPrayerIds = prayerRepository.saveAll(newPrayers)
                .stream()
                .map(Prayer::getId)
                .collect(Collectors.toList());
        card.setPrayers(newPrayerIds);
    }
}
