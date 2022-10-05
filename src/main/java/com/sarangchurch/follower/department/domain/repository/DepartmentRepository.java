package com.sarangchurch.follower.department.domain.repository;

import com.sarangchurch.follower.department.domain.model.Department;

import java.util.Optional;

public interface DepartmentRepository {
    Department save(Department department);

    Optional<Department> findById(Long id);
}
