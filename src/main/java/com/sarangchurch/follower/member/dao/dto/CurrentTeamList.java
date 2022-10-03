package com.sarangchurch.follower.member.dao.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrentTeamList {
    private final Long teamId;
    private final String teamName;
}
