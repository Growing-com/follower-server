package com.sarangchurch.follower.member.domain.model;

import com.sarangchurch.follower.member.domain.model.Favorite;
import com.sarangchurch.follower.member.domain.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FavoriteTest {

    @DisplayName("즐겨찾기 한 사람과 즐겨찾기 대상이 같으면 동등성 비교 성공")
    @Test
    void equals() {
        assertThat(new Favorite(Member.builder().id(1L).build(), 2L))
                .isEqualTo(new Favorite(Member.builder().id(1L).build(), 2L));
    }
}
