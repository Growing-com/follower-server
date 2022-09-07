package com.sarangchurch.follower.aceeptance.auth;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.권한_검증에_실패한다;
import static com.sarangchurch.follower.aceeptance.auth.AuthAssertions.토큰_발급에_성공한다;
import static com.sarangchurch.follower.aceeptance.auth.AuthSteps.로그인;
import static com.sarangchurch.follower.aceeptance.auth.AuthSteps.토큰_재발급;

class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인하면 엑세스 토큰, 리프레쉬 토큰이 발급된다.")
    @Test
    void login() {
        토큰_발급에_성공한다(로그인("admin", "password"));
    }

    @DisplayName("아아디/비밀번호가 일치하지 않으면 로그인에 실패한다.")
    @Test
    void login_Fail() {
        권한_검증에_실패한다(로그인("admin12", "password12"));
    }

    @DisplayName("리프레쉬 토큰으로 새로운 토큰을 발급받을 수 있다.")
    @Test
    void refresh() {
        String refreshToken = 로그인("admin", "password").jsonPath().getString("refreshToken");

        토큰_발급에_성공한다(토큰_재발급(refreshToken));
    }
}
