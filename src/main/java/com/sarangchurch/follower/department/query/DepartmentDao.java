package com.sarangchurch.follower.department.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sarangchurch.follower.department.query.dto.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sarangchurch.follower.department.command.domain.model.QDepartment.department;

@Repository
@RequiredArgsConstructor
public class DepartmentDao {

    private final JPAQueryFactory queryFactory;

    public DepartmentResponse findById(Long departmentId) {
        return queryFactory
                .select(Projections.constructor(DepartmentResponse.class,
                        department.id.as("departmentId"),
                        department.name.as("name"),
                        Projections.constructor(DepartmentResponse.DepartmentInformationResponse.class,
                                department.information.ministerName,
                                department.information.ministerPhoneNumber,
                                department.information.location),
                        Projections.constructor(DepartmentResponse.DepartmentLinksResponse.class,
                                department.links.youtubeLink,
                                department.links.instagramLink,
                                department.links.churchLink)
                ))
                .from(department)
                .where(department.id.eq(departmentId))
                .fetchOne();
    }
}
