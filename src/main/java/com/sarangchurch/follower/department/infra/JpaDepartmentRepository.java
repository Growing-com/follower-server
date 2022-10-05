package com.sarangchurch.follower.department.infra;

import com.sarangchurch.follower.department.domain.model.Department;
import com.sarangchurch.follower.department.domain.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDepartmentRepository extends DepartmentRepository, JpaRepository<Department, Long> {
}
