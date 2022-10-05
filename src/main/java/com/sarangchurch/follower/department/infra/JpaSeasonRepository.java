package com.sarangchurch.follower.department.infra;

import com.sarangchurch.follower.department.domain.model.Season;
import com.sarangchurch.follower.department.domain.repository.SeasonRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeasonRepository extends JpaRepository<Season, Long>, SeasonRepository {
}
