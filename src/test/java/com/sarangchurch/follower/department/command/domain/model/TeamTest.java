package com.sarangchurch.follower.department.command.domain.model;

import com.sarangchurch.follower.department.command.domain.exception.DuplicateMemberException;
import com.sarangchurch.follower.department.command.domain.exception.IllegalTeamCodeException;
import com.sarangchurch.follower.department.command.domain.service.TeamMemberValidator;
import com.sarangchurch.follower.member.command.domain.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.auth.domain.model.RoleType.LEADER;
import static com.sarangchurch.follower.auth.domain.model.RoleType.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TeamTest {
    private TeamMemberValidator teamMemberValidator = new TeamMemberValidator();

    @DisplayName("가입 코드가 불일치하면 가입에 실패한다.")
    @Test
    void addMember_Exception1() {
        // given
        TeamCode code = new TeamCode();
        Team team = Team.builder()
                .code(code)
                .build();

        Member member = Member.builder()
                .id(1L)
                .build();

        // expected
        TeamCode wrongCode = new TeamCode();
        assertThatThrownBy(() -> team.addMember(teamMemberValidator, member, wrongCode))
                .isInstanceOf(IllegalTeamCodeException.class);
    }

    @DisplayName("한 팀에 두 번 이상 가입할 수 없다.")
    @Test
    void addMember_Exception2() {
        // given
        TeamCode code = new TeamCode();
        Team team = Team.builder()
                .code(code)
                .build();

        Member member = Member.builder()
                .id(1L)
                .build();

        team.addMember(teamMemberValidator, member, code);

        // expected
        assertThatThrownBy(() -> team.addMember(teamMemberValidator, member, code))
                .isInstanceOf(DuplicateMemberException.class);
    }

    @DisplayName("팀에 처음으로 가입한 멤버는 리더 이상의 역할이 된다.")
    @Test
    void addMember() {
        // given
        TeamCode code = new TeamCode();
        Team team = Team.builder()
                .code(code)
                .build();

        Member firstMember = Member.builder()
                .id(1L)
                .role(MEMBER)
                .build();

        Member secondMember = Member.builder()
                .id(2L)
                .role(MEMBER)
                .build();

        // when
        team.addMember(teamMemberValidator, firstMember, code);
        team.addMember(teamMemberValidator, secondMember, code);

        // then
        assertThat(firstMember.getRole()).isEqualTo(LEADER);
        assertThat(secondMember.getRole()).isEqualTo(MEMBER);
    }

}
