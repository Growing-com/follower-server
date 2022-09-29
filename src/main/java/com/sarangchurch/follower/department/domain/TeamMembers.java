package com.sarangchurch.follower.department.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMembers {
    @OneToMany(mappedBy = "team", cascade = ALL, orphanRemoval = true, fetch = EAGER)
    private List<TeamMember> teamMembers = new ArrayList<>();

    void add(Team team, Long memberId) {
        teamMembers.add(new TeamMember(team, memberId));
    }
}
