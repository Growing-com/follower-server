package com.sarangchurch.follower.department.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ministerName;
    private String ministerPhone;

    @Builder
    public Department(String name, String ministerName, String ministerPhone) {
        this.name = name;
        this.ministerName = ministerName;
        this.ministerPhone = ministerPhone;
    }

    public Long getId() {
        return id;
    }
}
