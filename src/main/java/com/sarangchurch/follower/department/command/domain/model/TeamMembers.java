package com.sarangchurch.follower.department.command.domain.model;

import com.sarangchurch.follower.common.types.marker.ValueObject;
import com.sarangchurch.follower.department.command.domain.exception.DuplicateMemberException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@ValueObject
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMembers {
    @OneToMany(mappedBy = "team", cascade = ALL, orphanRemoval = true, fetch = EAGER)
    private List<TeamMember> teamMembers = new ArrayList<>();

    void add(Team team, Long memberId) {
        TeamMember teamMember = new TeamMember(team, memberId);

        if (teamMembers.contains(teamMember)) {
            throw new DuplicateMemberException();
        }
        teamMembers.add(teamMember);
    }

    int size() {
        return teamMembers.size();
    }
}
