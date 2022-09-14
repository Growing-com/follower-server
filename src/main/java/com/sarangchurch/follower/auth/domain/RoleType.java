package com.sarangchurch.follower.auth.domain;

import com.sarangchurch.follower.common.EnumType;

public enum RoleType implements EnumType {
    ADMIN("웹 관리자"),
    MANAGER("앱 관리자"),
    LEADER("리더/코디"),
    MEMBER("조원");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public boolean matchRole(RoleType roleType) {
        return this == roleType;
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
