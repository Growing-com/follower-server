package com.sarangchurch.follower.member.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    @DisplayName("즐겨찾기를 추가 및 삭제할 수 있다.")
    @Test
    void toggleFavorite() {
        Member member = Member.builder()
                .id(1L)
                .build();

        assertThatNoException().isThrownBy(() -> {
            member.toggleFavorite(2L);
            member.toggleFavorite(2L);
        });
    }

    @DisplayName("자기 자신을 즐겨찾기 할 수 없다.")
    @Test
    void toggleFavorite_Exception() {
        Member member = Member.builder()
                .id(1L)
                .build();

        assertThatThrownBy(() -> member.toggleFavorite(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자기 자신을 즐겨찾기 할 수 없습니다.");
    }

    @DisplayName("id가 같으면 동등성 비교 성공")
    @Test
    void equals() {
        assertThat(Member.builder().id(1L).build()).isEqualTo(Member.builder().id(1L).build());
    }
}
