package com.sarangchurch.follower.department.domain.model;

import com.sarangchurch.follower.common.types.marker.ValueObject;

import java.util.Objects;
import java.util.UUID;

@ValueObject
public class TeamCode {

    private String code;

    public TeamCode() {
        this.code = UUID.randomUUID().toString().toUpperCase().substring(0, 6);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamCode teamCode = (TeamCode) o;
        return Objects.equals(code, teamCode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
