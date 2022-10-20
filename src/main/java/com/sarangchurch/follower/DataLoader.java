package com.sarangchurch.follower;

import com.sarangchurch.follower.auth.domain.model.RoleType;
import com.sarangchurch.follower.department.domain.model.Department;
import com.sarangchurch.follower.department.domain.model.Season;
import com.sarangchurch.follower.department.domain.model.Team;
import com.sarangchurch.follower.department.domain.model.TeamCode;
import com.sarangchurch.follower.department.domain.repository.DepartmentRepository;
import com.sarangchurch.follower.department.domain.repository.SeasonRepository;
import com.sarangchurch.follower.department.domain.repository.TeamRepository;
import com.sarangchurch.follower.department.domain.service.TeamMemberValidator;
import com.sarangchurch.follower.member.domain.model.Member;
import com.sarangchurch.follower.member.domain.repository.MemberRepository;
import com.sarangchurch.follower.prayer.domain.model.Card;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.model.Week;
import com.sarangchurch.follower.prayer.domain.repository.CardRepository;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sarangchurch.follower.auth.domain.model.RoleType.*;
import static com.sarangchurch.follower.member.domain.model.Gender.MALE;

@Profile("local")
@RequiredArgsConstructor
@Component
public class DataLoader {
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final SeasonRepository seasonRepository;
    private final TeamRepository teamRepository;
    private final CardRepository cardRepository;
    private final PrayerRepository prayerRepository;
    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;
    private final TeamMemberValidator teamMemberValidator;

    @Transactional
    public void loadData() {
        // 대학부 회원 2명
        Department univ = departmentRepository.save(createDepartment("대학 8부", "이순종 목사"));
        Member 이순종 = createMember("soonjong", "이순종", MANAGER, univ);
        Member 우상욱 = createMember("sangwook", "우상욱", MEMBER, univ);
        memberRepository.save(이순종);
        memberRepository.save(우상욱);

        em.flush();
        em.clear();

        // 대학부 기도 카드 30개
        saveCards(univ, 이순종);

        // 대학부 시즌 2개
        Season univPastSeason = seasonRepository.save(Season.builder().name("2022-1학기").departmentId(univ.getId()).isActive(false).build());
        Season univCurrentSeason = seasonRepository.save(Season.builder().name("2022-2학기").departmentId(univ.getId()).isActive(true).build());

        // 대학부 팀 3개
        teamRepository.save(createTeam(univPastSeason, "1학기 순모임", 우상욱));
        teamRepository.save(createTeam(univCurrentSeason, "2학기 순모임", 이순종, 우상욱));
        teamRepository.save(createTeam(univCurrentSeason, "간사 모임", 이순종));

        // 청년부 회원 2명
        Department youth = departmentRepository.save(createDepartment("청년 6부", "이원준 목사"));
        Member 이원준 = memberRepository.save(createMember("wonjun", "이원준", MANAGER, youth));
        Member 이종민 = memberRepository.save(createMember("jongmin", "이종민", LEADER, youth));

        // 청년부 시즌 1개
        Season youthCurrentSeason = seasonRepository.save(Season.builder().name("2022-2학기").departmentId(youth.getId()).isActive(true).build());

        // 청년부 팀 1개
        teamRepository.save(createTeam(youthCurrentSeason, "청년부 모임", 이원준, 이종민));
    }

    private Department createDepartment(String name, String ministerName) {
        return Department.builder()
                .name(name)
                .ministerName(ministerName)
                .ministerPhone("010-1234-1234")
                .build();
    }

    private Member createMember(String username, String name, RoleType roleType, Department department) {
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode("password"))
                .name(name)
                .birthDate(LocalDate.of(1991, 11, 1))
                .earlyBorn(false)
                .gender(MALE)
                .role(roleType)
                .departmentId(department.getId())
                .build();
    }

    private Team createTeam(Season past, String name, Member... members) {
        TeamCode code = new TeamCode();

        Team team = Team.builder()
                .name(name)
                .seasonId(past.getId())
                .code(code)
                .build();

        for (Member member : members) {
            team.addMember(teamMemberValidator, member, code);
        }

        return team;
    }

    private void saveCards(Department univ, Member 이순종) {
        Week week = Week.previousSunday(LocalDate.now());

        for (int i = 0; i < 100; i++) {
            week = week.lastWeek();
            Card card = cardRepository.save(Card.builder()
                    .memberId(이순종.getId())
                    .week(week)
                    .departmentId(univ.getId())
                    .build());
            List<Prayer> prayers = prayerRepository.saveAll(List.of(
                    Prayer.builder().responded(true).content("응답기도" + i).initialCardId(card.getId()).memberId(이순종.getId()).build(),
                    Prayer.builder().responded(false).content("대기기도" + i).initialCardId(card.getId()).memberId(이순종.getId()).build()
            ));
            card.update(prayers);

            cardRepository.save(card);
        }
    }
}
