package com.sarangchurch.follower.auth.application;

import com.sarangchurch.follower.auth.domain.RefreshToken;
import com.sarangchurch.follower.auth.domain.RefreshTokenRepository;
import com.sarangchurch.follower.auth.domain.exception.RefreshTokenExpiredException;
import com.sarangchurch.follower.auth.domain.exception.RefreshTokenNotFoundException;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;

@Service
@Transactional
public class RefreshTokenService {
    @Value("${jwt.refresh-token-valid-time-in-milliseconds}")
    private long refreshTokenExpirationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               UserDetailsService userDetailsService,
                               JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    public String create(String username) {
        refreshTokenRepository.deleteByUsername(username);
        refreshTokenRepository.flush();

        RefreshToken refreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                now().plus(refreshTokenExpirationMs, MILLIS),
                username
        );

        return refreshTokenRepository.save(refreshToken).getToken();
    }

    public TokenResponse refresh(String token) {
        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);

        if (oldRefreshToken.isExpired(now())) {
            throw new RefreshTokenExpiredException();
        }

        String accessToken = generateAccessToken(oldRefreshToken.getUsername());
        RefreshToken newRefreshToken = replaceOldRefreshToken(oldRefreshToken);
        return new TokenResponse(accessToken, newRefreshToken.getToken());
    }

    private String generateAccessToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        return jwtUtils.generateAccessToken(authentication);
    }

    private RefreshToken replaceOldRefreshToken(RefreshToken oldRefreshToken) {
        RefreshToken newRefreshToken = new RefreshToken(
                UUID.randomUUID().toString(),
                now().plus(refreshTokenExpirationMs, MILLIS),
                oldRefreshToken.getUsername()
        );

        refreshTokenRepository.delete(oldRefreshToken);
        refreshTokenRepository.flush();
        return refreshTokenRepository.save(newRefreshToken);
    }
}
