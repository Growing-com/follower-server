package com.sarangchurch.follower.prayer.domain;

import com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException;
import com.sarangchurch.follower.prayer.domain.exception.IllegalPrayerOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException.NOT_MY_PRAYER;
import static com.sarangchurch.follower.prayer.domain.exception.CantLinkPrayerException.SAME_INITIAL_CARD;
import static com.sarangchurch.follower.prayer.domain.exception.IllegalPrayerOperationException.CANT_CHANGE_CARD;
import static com.sarangchurch.follower.prayer.domain.exception.IllegalPrayerOperationException.CANT_CHANGE_MEMBER;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrayerTest {

    @DisplayName("기도의 주인을 변경할 수 없다.")
    @Test
    void toMember_Exception() {
        Prayer prayer = Prayer.builder()
                .content("hello")
                .responded(false)
                .build();

        prayer.toMember(1L);

        assertThatThrownBy(() -> prayer.toMember(2L))
                .isInstanceOf(IllegalPrayerOperationException.class)
                .hasMessage(CANT_CHANGE_MEMBER);
    }

    @DisplayName("기도의 시작 카드를 변경할 수 없다.")
    @Test
    void toCard_Exception() {
        Prayer prayer = Prayer.builder()
                .content("hello")
                .responded(false)
                .build();

        prayer.toCard(1L);

        assertThatThrownBy(() -> prayer.toCard(2L))
                .isInstanceOf(IllegalPrayerOperationException.class)
                .hasMessage(CANT_CHANGE_CARD);
    }

    @DisplayName("최초에 기도 주인과 시작 카드를 설정할 수 있다.")
    @Test
    void toMember_toCard() {
        assertThatNoException().isThrownBy(() -> Prayer.builder()
                .content("hello")
                .responded(false)
                .build()
                .toMember(2L)
                .toCard(1L));
    }

    @DisplayName("내 기도, 다른 시작 카드인 기도만 연결할 수 있다.")
    @Test
    void validateLinkable_Exception() {
        Prayer prayer = Prayer.builder()
                .content("hello")
                .responded(false)
                .build()
                .toMember(2L)
                .toCard(1L);

        assertThatThrownBy(() -> prayer.validateLinkable(1L, 2L))
                .isInstanceOf(CantLinkPrayerException.class)
                .hasMessage(NOT_MY_PRAYER);

        assertThatThrownBy(() -> prayer.validateLinkable(2L, 1L))
                .isInstanceOf(CantLinkPrayerException.class)
                .hasMessage(SAME_INITIAL_CARD);
    }
}
