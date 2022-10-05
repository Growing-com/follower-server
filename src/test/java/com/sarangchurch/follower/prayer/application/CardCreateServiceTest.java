package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.member.domain.model.Member;
import com.sarangchurch.follower.prayer.application.doubles.MemoryCardRepository;
import com.sarangchurch.follower.prayer.application.doubles.MemoryPrayerRepository;
import com.sarangchurch.follower.prayer.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.model.Week;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@Import(CardCreateServiceTestConfig.class)
class CardCreateServiceTest {
    private static final Member MEMBER = Member.builder().id(1L).build();

    @Autowired
    private CardCreateService cardCreateService;
    @Autowired
    private MemoryCardRepository cardRepository;
    @Autowired
    private MemoryPrayerRepository prayerRepository;

    @BeforeEach
    void setUp() {
        cardRepository.clear();
        prayerRepository.clear();
    }

    @DisplayName("새로운 카드를 작성하면 카드와 기도가 생성된다.")
    @Test
    void create_Content() {
        // given
        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello1"),
                new CardCreate.PrayerCreate(null, "hello2"),
                new CardCreate.PrayerCreate(null, "hello3")
        ));

        // when
        cardCreateService.create(MEMBER, request, Week.previousSunday(now()));

        // then
        assertAll(
                () -> assertThat(cardRepository.size()).isEqualTo(1L),
                () -> assertThat(prayerRepository.size()).isEqualTo(3L)
        );
    }

    @DisplayName("이전 기도를 카드에 연결하면 기도가 새로 생성되지 않는다.")
    @Test
    void create_LinkPrayers() {
        // given
        Prayer prayer = Prayer.builder()
                .memberId(MEMBER.getId())
                .initialCardId(5000L)
                .responded(false)
                .build();

        List<Prayer> prayers = prayerRepository.saveAll(Collections.singleton(prayer));

        // when
        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(prayers.get(0).getId(), null)
        ));
        cardCreateService.create(MEMBER, request, Week.previousSunday(now()));

        // then
        assertAll(
                () -> assertThat(cardRepository.size()).isEqualTo(1L),
                () -> assertThat(prayerRepository.size()).isEqualTo(1L)
        );
    }

    @DisplayName("존재하지 않는 기도를 카드에 연결할 수 없다.")
    @Test
    void create_LinkPrayers_Exception() {
        // given
        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(999L, null)
        ));

        // expected
        assertThatThrownBy(() -> cardCreateService.create(MEMBER, request, Week.previousSunday(now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 기도 입니다.");

    }

    @DisplayName("같은 주차의 카드를 다시 작성하면, 이전 카드와 기도들이 사라진다.")
    @Test
    void update() {
        // given
        CardCreate previousRequest = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello1")
        ));

        cardCreateService.create(MEMBER, previousRequest, Week.previousSunday(now()));

        // when
        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello2")
        ));
        cardCreateService.create(MEMBER, request, Week.previousSunday(now()));

        // then
        assertAll(
                () -> assertThat(cardRepository.size()).isEqualTo(1L),
                () -> assertThat(prayerRepository.size()).isEqualTo(1L)
        );
    }
}














