package com.sarangchurch.follower.department.command.infra;

import com.sarangchurch.follower.department.command.domain.model.Department;
import com.sarangchurch.follower.department.command.domain.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDepartmentRepository extends DepartmentRepository, JpaRepository<Department, Long> {
}
