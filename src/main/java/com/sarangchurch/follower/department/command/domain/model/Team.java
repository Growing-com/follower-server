package com.sarangchurch.follower.department.command.domain.model;

import com.sarangchurch.follower.department.command.domain.service.TeamMemberValidator;
import com.sarangchurch.follower.member.command.domain.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

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

    public void addMember(TeamMemberValidator teamMemberValidator, Member member, TeamCode code) {
        teamMemberValidator.validate(this, member, code);
        teamMembers.add(this, member.getId());
    }

    public void changeLeader(Member member) {
        this.leaderId = member.getId();
    }

    public boolean matchCode(TeamCode code) {
        return this.code.equals(code);
    }

    public int totalMembers() {
        return teamMembers.size();
    }

    public Long getId() {
        return id;
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
