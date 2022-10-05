package com.sarangchurch.follower.auth.domain.model;

import com.sarangchurch.follower.auth.domain.exception.RefreshTokenExpiredException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RefreshTokenTest {

    @DisplayName("만료된 토큰을 검증한다.")
    @Test
    void validateExpiredToken() {
        assertThatThrownBy(() -> {
            RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), now(), 1L);
            refreshToken.validateExpiration(now().plusSeconds(10));
        }).isInstanceOf(RefreshTokenExpiredException.class);
    }

    @DisplayName("유효한 토큰을 검증한다.")
    @Test
    void validateValidToken() {
        assertThatNoException().isThrownBy(() -> {
            RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), now().plusSeconds(10), 1L);
            refreshToken.validateExpiration(now());
        });
    }
}
