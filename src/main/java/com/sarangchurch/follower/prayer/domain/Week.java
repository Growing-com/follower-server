package com.sarangchurch.follower.prayer.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Week {

    private LocalDate week;

    public static Week previousSunday(LocalDate date) {
        return new Week(date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)));
    }

    private Week(LocalDate week) {
        this.week = week;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week week1 = (Week) o;
        return Objects.equals(week, week1.week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(week);
    }
}
