package com.sarangchurch.follower.department.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    private Long memberId;

    TeamMember(Team team, Long memberId) {
        this.team = team;
        this.memberId = memberId;
    }
}
