package com.sarangchurch.follower.department.domain.model;

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
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private DepartmentInformation information;
    @Embedded
    private DepartmentLinks links;

    @Builder
    public Department(String name, DepartmentInformation information, DepartmentLinks links) {
        this.name = name;
        this.information = information;
        this.links = links;
    }

    public Long getId() {
        return id;
    }
}
