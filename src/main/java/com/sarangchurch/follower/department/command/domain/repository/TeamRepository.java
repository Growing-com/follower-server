package com.sarangchurch.follower.department.command.domain.repository;

import com.sarangchurch.follower.department.command.domain.model.Team;

import java.util.Optional;

public interface TeamRepository {
    Optional<Team> findById(Long id);
    Team save(Team team);
}
