package com.sarangchurch.follower.department.command.integrate;

import com.sarangchurch.follower.department.command.domain.exception.InactiveSeasonException;
import com.sarangchurch.follower.department.command.domain.model.Season;
import com.sarangchurch.follower.department.command.domain.model.Team;
import com.sarangchurch.follower.department.command.domain.model.TeamCode;
import com.sarangchurch.follower.department.command.domain.repository.SeasonRepository;
import com.sarangchurch.follower.department.command.domain.repository.TeamRepository;
import com.sarangchurch.follower.department.command.domain.service.TeamMemberAssigner;
import com.sarangchurch.follower.member.command.application.MemberAssigner;
import com.sarangchurch.follower.member.command.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class MemberAssignerImpl implements MemberAssigner {
    private final TeamRepository teamRepository;
    private final SeasonRepository seasonRepository;
    private final TeamMemberAssigner teamMemberAssigner;

    @Override
    @Transactional
    public void assign(Member member, String teamCode) {
        TeamCode code = new TeamCode(teamCode);

        Team team = teamRepository.findByCode(code)
                .orElseThrow(EntityNotFoundException::new);

        Season season = seasonRepository.findById(team.getSeasonId())
                .orElseThrow(EntityNotFoundException::new);

        if (!season.isActive()) {
            throw new InactiveSeasonException();
        }

        member.toDepartment(season.getDepartmentId());
        team.addMember(teamMemberAssigner, member, code);
    }
}
