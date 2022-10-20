package com.sarangchurch.follower.department.domain.model;

import com.sarangchurch.follower.common.types.marker.ValueObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@ValueObject
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeasonPeriod {

    private LocalDate startDate;
    private LocalDate endDate;

    SeasonPeriod(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료 날짜가 시작 날짜보다 앞설 수 없습니다.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeasonPeriod that = (SeasonPeriod) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
