package com.sarangchurch.follower.prayer.domain;

import com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException.NOT_MY_PRAYER;
import static com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException.SAME_INITIAL_CARD;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrayerTest {

    @DisplayName("내 기도, 다른 시작 카드인 기도만 연결할 수 있다.")
    @Test
    void validateLinkable_Exception() {
        Prayer prayer = Prayer.builder()
                .content("hello")
                .responded(false)
                .initialCardId(1L)
                .memberId(2L)
                .build();

        assertThatThrownBy(() -> prayer.validateLinkable(1L, 2L))
                .isInstanceOf(CantLinkPrayerException.class)
                .hasMessage(NOT_MY_PRAYER);

        assertThatThrownBy(() -> prayer.validateLinkable(2L, 1L))
                .isInstanceOf(CantLinkPrayerException.class)
                .hasMessage(SAME_INITIAL_CARD);
    }
}
