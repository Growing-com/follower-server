package com.sarangchurch.follower.department.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SeasonPeriodTest {

    @DisplayName("종료 날짜가 시작 날짜와 동일하거나 과거면 생성 실패")
    @Test
    void validation() {
        assertThatThrownBy(() -> new SeasonPeriod(
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 3, 28)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("종료 날짜가 시작 날짜보다 앞설 수 없습니다.");
    }

    @DisplayName("시작 날짜와 종료 날짜가 동일하면 동등성 검사 성공")
    @Test
    void equals() {
        SeasonPeriod seasonPeriod1 = new SeasonPeriod(
                LocalDate.of(2022, 9, 1),
                LocalDate.of(2023, 3, 28)
        );
        SeasonPeriod seasonPeriod2 = new SeasonPeriod(
                LocalDate.of(2022, 9, 1),
                LocalDate.of(2023, 3, 28)
        );

        assertThat(seasonPeriod1).isEqualTo(seasonPeriod2);
    }
}
