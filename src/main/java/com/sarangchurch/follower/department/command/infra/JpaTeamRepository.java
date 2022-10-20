package com.sarangchurch.follower.department.command.infra;

import com.sarangchurch.follower.department.command.domain.model.Team;
import com.sarangchurch.follower.department.command.domain.repository.TeamRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<Team, Long>, TeamRepository {
}
