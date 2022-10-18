package com.sarangchurch.follower.department.domain.model;

import com.sarangchurch.follower.department.domain.exception.IllegalTeamCodeException;
import com.sarangchurch.follower.member.domain.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

import static com.sarangchurch.follower.auth.domain.model.RoleType.LEADER;
import static com.sarangchurch.follower.auth.domain.model.RoleType.MEMBER;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long seasonId;
    private String name;
    private Long leaderId;
    @Embedded
    private TeamCode code;
    @Embedded
    private TeamMembers teamMembers = new TeamMembers();

    @Builder
    public Team(Long seasonId, String name, Long leaderId, TeamCode code) {
        this.seasonId = seasonId;
        this.name = name;
        this.leaderId = leaderId;
        this.code = code;
    }

    public void addMember(Member member, TeamCode code) {
        validateCode(code);
        setLeader(member);
        teamMembers.add(this, member.getId());
    }

    private void validateCode(TeamCode code) {
        if (!this.code.equals(code)) {
            throw new IllegalTeamCodeException();
        }
    }

    private void setLeader(Member member) {
        if (leaderId != null) {
            return;
        }
        if (member.getRole() == MEMBER) {
            member.changeRole(LEADER);
        }
        this.leaderId = member.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
