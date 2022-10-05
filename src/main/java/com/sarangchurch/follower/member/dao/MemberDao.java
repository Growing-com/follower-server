package com.sarangchurch.follower.member.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sarangchurch.follower.member.dao.dto.CurrentTeam;
import com.sarangchurch.follower.member.dao.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sarangchurch.follower.department.domain.QDepartment.department;
import static com.sarangchurch.follower.department.domain.QSeason.season;
import static com.sarangchurch.follower.department.domain.QTeam.team;
import static com.sarangchurch.follower.department.domain.QTeamMember.teamMember;
import static com.sarangchurch.follower.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final JPAQueryFactory queryFactory;

    public MemberDetails findMemberInfo(Long memberId) {
        return queryFactory
                .select(Projections.constructor(
                        MemberDetails.class,
                        member.name,
                        member.birthDate,
                        department.name.as("departmentName"),
                        department.id.as("departmentId"),
                        member.role.roleType
                )).distinct()
                .from(member)
                .join(department).on(department.id.eq(member.departmentId), member.id.eq(memberId))
                .fetchOne();
    }

    public List<CurrentTeam> findCurrentTeam(Long memberId) {
        return queryFactory
                .select(Projections.constructor(
                        CurrentTeam.class,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                ))
                .from(team)
                .join(teamMember).on(teamMember.team.id.eq(team.id), teamMember.memberId.eq(memberId))
                .join(season).on(season.id.eq(team.seasonId), season.isActive.isTrue())
                .fetch();
    }
}
