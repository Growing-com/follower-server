package com.sarangchurch.follower.department.domain.service;

import com.sarangchurch.follower.common.types.marker.DomainService;
import com.sarangchurch.follower.department.domain.exception.IllegalTeamCodeException;
import com.sarangchurch.follower.department.domain.model.Team;
import com.sarangchurch.follower.department.domain.model.TeamCode;
import com.sarangchurch.follower.member.domain.model.Member;
import org.springframework.transaction.annotation.Transactional;

import static com.sarangchurch.follower.auth.domain.model.RoleType.LEADER;
import static com.sarangchurch.follower.auth.domain.model.RoleType.MEMBER;

@DomainService
public class TeamMemberValidator {

    @Transactional
    public void validate(Team team, Member member, TeamCode code) {
        if (!team.matchCode(code)) {
            throw new IllegalTeamCodeException();
        }

        if (team.totalMembers() == 0) {
            setTeamLeader(team, member);
        }
    }

    private void setTeamLeader(Team team, Member member) {
        if (member.getRole() == MEMBER) {
            member.changeRole(LEADER);
        }
        team.changeLeader(member);
    }

}
