package com.sarangchurch.follower.prayer.domain.model;

import com.sarangchurch.follower.prayer.application.doubles.MemoryPrayerRepository;
import com.sarangchurch.follower.prayer.domain.exception.DuplicatePrayerException;
import com.sarangchurch.follower.prayer.domain.service.CardUpdater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    private final MemoryPrayerRepository prayerRepository = new MemoryPrayerRepository();
    private final CardUpdater cardUpdater = new CardUpdater(prayerRepository);

    @DisplayName("중복된 기도를 카드에 넣을 수 없다.")
    @Test
    void update_Exception() {
        // given
        Card card = Card.builder()
                .memberId(1L)
                .departmentId(1L)
                .week(Week.previousSunday(now()))
                .build();

        Prayer prayer = Prayer.builder()
                .initialCardId(1L)
                .build();

        prayerRepository.saveAll(List.of(prayer));

        // expected
        assertThatThrownBy(() -> card.update(cardUpdater, List.of(prayer, prayer)))
                .isInstanceOf(DuplicatePrayerException.class);
    }

    @DisplayName("카드에 기도를 세팅할 수 있다.")
    @Test
    void update() {
        // given
        Card card = Card.builder()
                .memberId(1L)
                .departmentId(1L)
                .week(Week.previousSunday(now()))
                .build();

        Prayer prayer = Prayer.builder()
                .initialCardId(1L)
                .build();

        Prayer anotherPrayer = Prayer.builder()
                .initialCardId(2L)
                .build();

        prayerRepository.saveAll(List.of(prayer, anotherPrayer));

        // expected
        assertThatNoException().isThrownBy(
                () -> card.update(cardUpdater, List.of(prayer, anotherPrayer))
        );
    }
}
