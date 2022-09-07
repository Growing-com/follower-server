package com.sarangchurch.follower.auth.infra;

import com.sarangchurch.follower.auth.domain.RefreshToken;
import com.sarangchurch.follower.auth.domain.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenRepository {
}
