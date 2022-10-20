package com.sarangchurch.follower.member.query.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CurrentTeam {
    private final Long teamId;
    private final String teamName;
}
