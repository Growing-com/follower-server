package com.sarangchurch.follower.prayer.domain.model;

import com.sarangchurch.follower.prayer.domain.model.Week;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class WeekTest {

    @DisplayName("가장 가까운 과거의 일요일을 반환한다.")
    @Test
    void previousSunday() {
        assertThat(Week.previousSunday(LocalDate.of(2022, 9, 28)))
                .isEqualTo(Week.previousSunday(LocalDate.of(2022, 9, 25)));
    }
}
