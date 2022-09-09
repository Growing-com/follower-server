package com.sarangchurch.follower.auth.application;

import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.domain.LoginMember;
import com.sarangchurch.follower.auth.domain.RefreshToken;
import com.sarangchurch.follower.auth.domain.RefreshTokenRepository;
import com.sarangchurch.follower.auth.domain.TokenUserLoader;
import com.sarangchurch.follower.auth.domain.exception.RefreshTokenNotFoundException;
import com.sarangchurch.follower.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;

@Service
@Transactional
public class AuthService {
    @Value("${jwt.refresh-token-valid-time-in-milliseconds}")
    private long refreshTokenExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenUserLoader tokenUserLoader;
    private final JwtUtils jwtUtils;

    public AuthService(RefreshTokenRepository refreshTokenRepository, TokenUserLoader tokenUserLoader, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenUserLoader = tokenUserLoader;
        this.jwtUtils = jwtUtils;
    }

    public TokenResponse login(LoginMember loginMember) {
        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(loginMember.getId())
                .orElse(new RefreshToken(
                        UUID.randomUUID(),
                        now().plus(refreshTokenExpirationMs, MILLIS),
                        loginMember.getId()
                ));

        String accessToken = jwtUtils.generateAccessToken(loginMember);
        refreshTokenRepository.save(refreshToken);
        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    public TokenResponse refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);

        refreshToken.validateExpiration(now());

        LoginMember loginMember = (LoginMember) tokenUserLoader.loadUserByUserId(refreshToken.getMemberId());
        String accessToken = jwtUtils.generateAccessToken(loginMember);
        refreshToken.renew(UUID.randomUUID(), now().plus(refreshTokenExpirationMs, MILLIS));
        return new TokenResponse(accessToken, refreshToken.getToken());
    }
}
