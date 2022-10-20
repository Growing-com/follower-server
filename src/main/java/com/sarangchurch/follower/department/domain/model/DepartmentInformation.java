package com.sarangchurch.follower.department.domain.model;

import com.sarangchurch.follower.common.types.vo.PhoneNumber;
import com.sarangchurch.follower.common.types.marker.ValueObject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@ValueObject
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentInformation {

    private String ministerName;
    private PhoneNumber ministerPhoneNumber;
    private String location;

    public DepartmentInformation(String ministerName, String phoneNumber, String location) {
        this.ministerName = ministerName;
        this.ministerPhoneNumber = new PhoneNumber(phoneNumber);
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentInformation that = (DepartmentInformation) o;
        return Objects.equals(ministerName, that.ministerName) && Objects.equals(ministerPhoneNumber, that.ministerPhoneNumber) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ministerName, ministerPhoneNumber, location);
    }
}
