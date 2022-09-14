package com.sarangchurch.follower.department.infra;

import com.sarangchurch.follower.department.domain.Department;
import com.sarangchurch.follower.department.domain.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDepartmentRepository extends DepartmentRepository, JpaRepository<Department, Long> {
}
