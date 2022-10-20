package com.sarangchurch.follower.department.command.doubles;

import com.sarangchurch.follower.department.command.domain.model.Team;
import com.sarangchurch.follower.department.command.domain.repository.TeamRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryTeamRepository implements TeamRepository {

    private final Map<Long, Team> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public Optional<Team> findById(Long id) {
        return store.values()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findAny();
    }

    @Override
    public Team save(Team team) {
        if (team.getId() == null) {
            sequence++;
            ReflectionTestUtils.setField(team, "id", sequence);
            store.put(sequence, team);
        }
        return team;
    }
}
