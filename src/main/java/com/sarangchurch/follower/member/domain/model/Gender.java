package com.sarangchurch.follower.member.domain.model;

import com.sarangchurch.follower.common.types.EnumType;

public enum Gender implements EnumType {
    MALE("남자"),
    FEMALE("여자");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
