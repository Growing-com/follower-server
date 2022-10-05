package com.sarangchurch.follower.department.infra;

import com.sarangchurch.follower.department.domain.model.Team;
import com.sarangchurch.follower.department.domain.repository.TeamRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long>, TeamRepository {
}
