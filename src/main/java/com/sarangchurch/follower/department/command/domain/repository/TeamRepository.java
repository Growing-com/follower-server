package com.sarangchurch.follower.department.command.domain.repository;

import com.sarangchurch.follower.department.command.domain.model.Team;
import com.sarangchurch.follower.department.command.domain.model.TeamCode;

import java.util.Optional;

public interface TeamRepository {
    Optional<Team> findById(Long id);
    Optional<Team> findByCode(TeamCode code);
    Team save(Team team);
}
