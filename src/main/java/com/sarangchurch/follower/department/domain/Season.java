package com.sarangchurch.follower.department.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long departmentId;
    @Embedded
    private SeasonPeriod period;
    private boolean isActive;

    @Builder
    public Season(String name, Long departmentId, SeasonPeriod period, boolean isActive) {
        this.name = name;
        this.departmentId = departmentId;
        this.period = period;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }
}
