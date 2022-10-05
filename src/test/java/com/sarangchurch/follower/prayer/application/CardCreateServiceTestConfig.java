package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.common.events.EventsConfiguration;
import com.sarangchurch.follower.prayer.application.doubles.MemoryCardRepository;
import com.sarangchurch.follower.prayer.application.doubles.MemoryPrayerRepository;
import com.sarangchurch.follower.prayer.domain.events.handler.CardRefreshedHandler;
import com.sarangchurch.follower.prayer.domain.repository.CardRepository;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import(EventsConfiguration.class)
public class CardCreateServiceTestConfig {

    @Bean
    CardRefreshedHandler cardRefreshedHandler() {
        return new CardRefreshedHandler(prayerRepository());
    }

    @Bean
    CardCreateService cardCreateService() {
        return new CardCreateService(cardRepository(), prayerRepository());
    }

    @Bean
    CardRepository cardRepository() {
        return new MemoryCardRepository();
    }

    @Bean
    PrayerRepository prayerRepository() {
        return new MemoryPrayerRepository();
    }
}
