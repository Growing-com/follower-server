package com.sarangchurch.follower.auth.infra;

import com.sarangchurch.follower.auth.domain.model.RefreshToken;
import com.sarangchurch.follower.auth.domain.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long>, RefreshTokenRepository {
}
