package com.sarangchurch.follower.prayer.domain.model;

import com.sarangchurch.follower.prayer.domain.exception.DuplicatePrayerException;
import com.sarangchurch.follower.prayer.domain.model.Card;
import com.sarangchurch.follower.prayer.domain.model.Week;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    @DisplayName("중복된 기도를 카드에 넣을 수 없다.")
    @Test
    void setPrayers_Exception() {
        Card card = Card.builder()
                .memberId(1L)
                .departmentId(1L)
                .week(Week.previousSunday(now()))
                .build();

        assertThatThrownBy(() -> card.setPrayers(List.of(1L, 1L, 2L)))
                .isInstanceOf(DuplicatePrayerException.class);
    }

    @DisplayName("카드에 기도를 세팅할 수 있다.")
    @Test
    void setPrayers() {
        Card card = Card.builder()
                .memberId(1L)
                .departmentId(1L)
                .week(Week.previousSunday(now()))
                .build();

        assertThatNoException().isThrownBy(
                () -> card.setPrayers(List.of(1L, 2L, 3L))
        );
    }
}
