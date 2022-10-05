package com.sarangchurch.follower.admin.domain.model;

public class Admin {
    private final Long id;
    private final Long departmentId;

    public Admin(Long id, Long departmentId) {
        this.id = id;
        this.departmentId = departmentId;
    }

    public boolean hasDepartmentAccess(Long departmentId) {
        return this.departmentId.equals(departmentId);
    }
}
