package com.sarangchurch.follower.department.infra;

import com.sarangchurch.follower.department.domain.Season;
import com.sarangchurch.follower.department.domain.SeasonRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSeasonRepository extends JpaRepository<Season, Long>, SeasonRepository {
}
