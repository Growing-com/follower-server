package com.sarangchurch.follower.auth.domain;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken findByMemberId(Long memberId);
}
