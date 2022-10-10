package com.sarangchurch.follower.member.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sarangchurch.follower.member.dao.dto.CurrentTeam;
import com.sarangchurch.follower.member.dao.dto.MemberSearchResult;
import com.sarangchurch.follower.member.dao.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sarangchurch.follower.department.domain.model.QDepartment.department;
import static com.sarangchurch.follower.department.domain.model.QSeason.season;
import static com.sarangchurch.follower.department.domain.model.QTeam.team;
import static com.sarangchurch.follower.department.domain.model.QTeamMember.teamMember;
import static com.sarangchurch.follower.member.domain.model.QFavorite.favorite;
import static com.sarangchurch.follower.member.domain.model.QMember.member;

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

    public List<MemberSearchResult> findMemberFavorites(Long memberId) {
        return queryFactory
                .select(Projections.constructor(
                        MemberSearchResult.class,
                        member.id.as("memberId"),
                        member.name,
                        member.birthDate,
                        member.gender
                ))
                .from(favorite)
                .join(member).on(member.id.eq(favorite.toMemberId))
                .where(favorite.fromMember.id.eq(memberId))
                .orderBy(member.name.asc())
                .fetch();
    }

    public List<MemberSearchResult> search(Long departmentId, String name, Pageable pageable) {
        return queryFactory
                .select(Projections.constructor(
                        MemberSearchResult.class,
                        member.id.as("memberId"),
                        member.name,
                        member.birthDate,
                        member.gender
                ))
                .from(member)
                .where(member.departmentId.eq(departmentId), member.name.like(name + "%"))
                .orderBy(member.name.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1L)
                .fetch();
    }
}
