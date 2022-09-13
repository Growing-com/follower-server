package com.sarangchurch.follower.auth.domain;

public enum RoleType {
    ADMIN,
    MANAGER,
    LEADER,
    MEMBER;

    public boolean matchRole(RoleType roleType) {
        return this == roleType;
    }
}
