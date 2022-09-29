package com.sarangchurch.follower.member.dao.dto;

import lombok.Getter;

@Getter
public class CurrentTeamInfo {
    private final Long teamId;
    private final String teamName;

    public CurrentTeamInfo(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
