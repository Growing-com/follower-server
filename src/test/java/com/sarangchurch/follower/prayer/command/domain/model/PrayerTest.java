package com.sarangchurch.follower.prayer.command.domain.model;

import com.sarangchurch.follower.auth.domain.exception.ForbiddenException;
import com.sarangchurch.follower.prayer.command.domain.exception.CantLinkPrayerException;
import com.sarangchurch.follower.prayer.command.domain.model.Prayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.prayer.command.domain.exception.CantLinkPrayerException.NOT_MY_PRAYER;
import static com.sarangchurch.follower.prayer.command.domain.exception.CantLinkPrayerException.SAME_INITIAL_CARD;
import static org.assertj.core.api.Assertions.assertThatNoException;
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

    @DisplayName("자신의 기도를 응답처리 할 수 있다.")
    @Test
    void respond() {
        Prayer prayer = Prayer.builder()
                .memberId(1L)
                .build();

        assertThatNoException().isThrownBy(() -> prayer.respond(1L));
    }

    @DisplayName("다른 사용자의 기도를 응답처리 할 수 없다.")
    @Test
    void respond_Exception() {
        Prayer prayer = Prayer.builder()
                .memberId(1L)
                .build();

        assertThatThrownBy(() -> prayer.respond(2L))
                .isInstanceOf(ForbiddenException.class);
    }
}
