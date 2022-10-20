package com.sarangchurch.follower.department.command.infra;

import com.sarangchurch.follower.department.command.domain.model.Season;
import com.sarangchurch.follower.department.command.domain.repository.SeasonRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeasonRepository extends JpaRepository<Season, Long>, SeasonRepository {
}
