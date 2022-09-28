package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.prayer.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.domain.Week;
import com.sarangchurch.follower.prayer.doubles.MemoryCardRepository;
import com.sarangchurch.follower.prayer.doubles.MemoryPrayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CardCreateServiceTest {

    private CardCreateService cardCreateService;
    private MemoryCardRepository cardRepository;
    private MemoryPrayerRepository prayerRepository;

    @BeforeEach
    void setUp() {
        cardRepository = new MemoryCardRepository();
        prayerRepository = new MemoryPrayerRepository();
        cardCreateService = new CardCreateService(cardRepository, prayerRepository);
    }

    @DisplayName("새로운 기도 카드를 작성한다.")
    @Test
    void create_Content() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello1"),
                new CardCreate.PrayerCreate(null, "hello2"),
                new CardCreate.PrayerCreate(null, "hello3")
        ));

        // when
        cardCreateService.create(member, request, Week.previousSunday(now()));

        // then
        assertAll(
                () -> assertThat(cardRepository.size()).isEqualTo(1L),
                () -> assertThat(prayerRepository.size()).isEqualTo(3L)
        );
    }

    @DisplayName("이전 기도를 새로운 기도 카에 연결한다.")
    @Test
    void create_LinkPrayers() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        CardCreate previousRequest = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello1")
        ));

        cardCreateService.create(member, previousRequest, Week.previousSunday(now().minusWeeks(1)));

        // when
        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(1L, null)
        ));
        cardCreateService.create(member, request, Week.previousSunday(now()));

        // then
        assertAll(
                () -> assertThat(cardRepository.size()).isEqualTo(2L),
                () -> assertThat(prayerRepository.size()).isEqualTo(1L)
        );
    }

    @DisplayName("존재하지 않는 기도를 연결할 수 없다.")
    @Test
    void create_LinkPrayers_Exception() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(1L, null)
        ));

        // expected
        assertThatThrownBy(() -> cardCreateService.create(member, request, Week.previousSunday(now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 기도 입니다.");

    }

    @DisplayName("같은 주차의 카드를 다시 작성하면, 이전 카드와 기도들이 사라진다.")
    @Test
    void update() {
        // given
        Member member = Member.builder()
                .id(1L)
                .build();

        CardCreate previousRequest = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello1")
        ));

        cardCreateService.create(member, previousRequest, Week.previousSunday(now()));

        // when
        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(null, "hello1")
        ));
        cardCreateService.create(member, request, Week.previousSunday(now()));

        // then
        assertAll(
                () -> assertThat(cardRepository.size()).isEqualTo(1L),
                () -> assertThat(prayerRepository.size()).isEqualTo(1L)
        );
    }
}
