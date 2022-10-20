package com.sarangchurch.follower.department.command.domain.repository;

import com.sarangchurch.follower.department.command.domain.model.Season;

public interface SeasonRepository {
    Season save(Season season);
}
