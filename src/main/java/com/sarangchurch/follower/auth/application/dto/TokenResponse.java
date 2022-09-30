package com.sarangchurch.follower.auth.application.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    private final String accessToken;
    private final String refreshToken;

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
