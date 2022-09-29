package com.sarangchurch.follower.department.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private TeamMembers teamMembers = new TeamMembers();

    @Builder
    public Team(Long seasonId, String name, Long leaderId) {
        this.seasonId = seasonId;
        this.name = name;
        this.leaderId = leaderId;
    }

    public void addMember(Long memberId) {
        teamMembers.add(memberId);
    }
}
