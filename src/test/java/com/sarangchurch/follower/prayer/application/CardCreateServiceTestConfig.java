package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.prayer.application.doubles.MemoryCardRepository;
import com.sarangchurch.follower.prayer.application.doubles.MemoryPrayerRepository;
import com.sarangchurch.follower.prayer.domain.repository.CardRepository;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CardCreateServiceTestConfig {

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
