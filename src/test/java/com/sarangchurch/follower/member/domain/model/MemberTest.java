package com.sarangchurch.follower.member.domain.model;

import com.sarangchurch.follower.member.domain.exception.IllegalFavoriteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
                .isInstanceOf(IllegalFavoriteException.class);
    }

    @DisplayName("즐겨찾기를 한 번에 수정한다.")
    @Test
    void bulkUpdateFavorites() {
        Member member = Member.builder()
                .id(1L)
                .build();

        List<Long> add = List.of(2L, 3L, 4L);
        List<Long> remove = List.of(5L, 6L);

        assertThatNoException().isThrownBy(() -> member.bulkUpdateFavorites(add, remove));
    }

    @DisplayName("자기 자신을 즐겨찾기 할 수 없다.")
    @Test
    void bulkUpdateFavorites_Exception() {
        Member member = Member.builder()
                .id(1L)
                .build();

        List<Long> add = List.of(1L);
        List<Long> remove = List.of(5L, 6L);

        assertThatThrownBy(() -> member.bulkUpdateFavorites(add, remove))
                .isInstanceOf(IllegalFavoriteException.class);
    }

    @DisplayName("id가 같으면 동등성 비교 성공")
    @Test
    void equals() {
        assertThat(Member.builder().id(1L).build()).isEqualTo(Member.builder().id(1L).build());
    }
}
