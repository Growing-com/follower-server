package com.sarangchurch.follower.department.domain;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String ministerName;
    @Column(nullable = false)
    private String ministerPhone;

    protected Department() {
    }

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
