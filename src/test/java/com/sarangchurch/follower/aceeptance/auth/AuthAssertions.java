package com.sarangchurch.follower.aceeptance.auth;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AuthAssertions {

    static void 토큰_발급에_성공한다(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getString("content.accessToken")).isNotNull();
        assertThat(response.jsonPath().getUUID("content.refreshToken")).isNotNull();
    }
}
