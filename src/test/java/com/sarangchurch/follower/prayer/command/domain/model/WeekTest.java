package com.sarangchurch.follower.prayer.command.domain.model;

import com.sarangchurch.follower.prayer.command.domain.model.Week;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class WeekTest {

    private final LocalDate sunday = LocalDate.of(2022, 9, 25);

    @DisplayName("가장 가까운 과거의 일요일을 반환한다.")
    @Test
    void previousSunday() {
        assertThat(Week.previousSunday(sunday))
                .isEqualTo(Week.previousSunday(sunday.plusDays(6)));
    }

    @DisplayName("지난 주 일요일을 반환한다.")
    @Test
    void lastWeek() {
        Week sundayWeek = Week.previousSunday(sunday);

        assertThat(sundayWeek.lastWeek())
                .isEqualTo(Week.previousSunday(LocalDate.of(2022, 9, 18)));
    }
}
