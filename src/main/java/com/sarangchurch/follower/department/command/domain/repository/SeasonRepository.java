package com.sarangchurch.follower.department.command.domain.repository;

import com.sarangchurch.follower.department.command.domain.model.Season;

import java.util.Optional;

public interface SeasonRepository {
    Optional<Season> findById(Long id);
    Season save(Season season);
}
