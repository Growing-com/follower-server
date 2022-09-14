package com.sarangchurch.follower.department.domain;

import java.util.Optional;

public interface DepartmentRepository {
    Department save(Department department);

    Optional<Department> findById(Long id);
}
