package com.sarangchurch.follower.department.infra;

import com.sarangchurch.follower.department.domain.Team;
import com.sarangchurch.follower.department.domain.TeamRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long>, TeamRepository {
}
