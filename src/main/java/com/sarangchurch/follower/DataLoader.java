package com.sarangchurch.follower;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.department.domain.Department;
import com.sarangchurch.follower.department.domain.DepartmentRepository;
import com.sarangchurch.follower.department.domain.Season;
import com.sarangchurch.follower.department.domain.SeasonRepository;
import com.sarangchurch.follower.department.domain.Team;
import com.sarangchurch.follower.department.domain.TeamRepository;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRepository;
import com.sarangchurch.follower.member.domain.MemberRole;
import com.sarangchurch.follower.prayer.domain.Card;
import com.sarangchurch.follower.prayer.domain.CardRepository;
import com.sarangchurch.follower.prayer.domain.Prayer;
import com.sarangchurch.follower.prayer.domain.PrayerRepository;
import com.sarangchurch.follower.prayer.domain.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.sarangchurch.follower.auth.domain.RoleType.*;
import static com.sarangchurch.follower.member.domain.Gender.MALE;

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

    @Transactional
    public void loadData() {
        // 대학부(회원 2명, 시즌 2개, 팀 3개)
        Department univ = departmentRepository.save(createDepartment("대학 8부", "이순종 목사"));

        Member 이순종 = createMember("soonjong", "이순종", MANAGER, univ);
        Member 우상욱 = createMember("sangwook", "우상욱", MEMBER, univ);
        memberRepository.save(이순종);
        memberRepository.save(우상욱);

        우상욱.toggleFavorite(이순종.getId());
        em.flush();
        em.clear();

        Card card = cardRepository.save(Card.builder()
                .memberId(이순종.getId())
                .week(Week.previousSunday(LocalDate.now()))
                .departmentId(univ.getId())
                .build());
        List<Prayer> prayers = prayerRepository.saveAll(List.of(
                Prayer.builder().responded(true).content("abcd").initialCardId(card.getId()).memberId(이순종.getId()).build(),
                Prayer.builder().responded(false).content("1234").initialCardId(card.getId()).memberId(이순종.getId()).build()
        ));
        card.setPrayers(List.of(prayers.get(0).getId(), prayers.get(1).getId()));

        Season univPastSeason = seasonRepository.save(Season.builder().name("2022-1학기").departmentId(univ.getId()).isActive(false).build());
        Season univCurrentSeason = seasonRepository.save(Season.builder().name("2022-2학기").departmentId(univ.getId()).isActive(true).build());

        teamRepository.save(createTeam(univPastSeason, "1학기 순모임", 우상욱));
        teamRepository.save(createTeam(univCurrentSeason, "2학기 순모임", 이순종, 우상욱));
        teamRepository.save(createTeam(univCurrentSeason, "간사 모임", 이순종));

        // 청년부(회원 2명, 시즌 1개, 팀 2개)
        Department youth = departmentRepository.save(createDepartment("청년 6부", "이원준 목사"));
        Member 이원준 = memberRepository.save(createMember("wonjun", "이원준", MANAGER, youth));
        Member 이종민 = memberRepository.save(createMember("jongmin", "이종민", LEADER, youth));

        Season youthCurrentSeason = seasonRepository.save(Season.builder().name("2022-2학기").departmentId(youth.getId()).isActive(true).build());

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
                .role(MemberRole.of(roleType))
                .departmentId(department.getId())
                .build();
    }

    private Team createTeam(Season past, String name, Member... members) {
        Team team = Team.builder()
                .name(name)
                .seasonId(past.getId())
                .build();

        for (Member member : members) {
            team.addMember(member.getId());
        }

        return team;
    }
}
