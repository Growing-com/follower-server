package com.sarangchurch.follower.aceeptance.auth;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.권한_검사에_실패한다;
import static com.sarangchurch.follower.aceeptance.CommonAssertions.인증에_실패한다;
import static com.sarangchurch.follower.aceeptance.auth.AuthAssertions.토큰_발급에_성공한다;
import static com.sarangchurch.follower.aceeptance.auth.AuthSteps.*;

class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인하면 엑세스 토큰, 리프레쉬 토큰이 발급된다.")
    @Test
    void login() {
        토큰_발급에_성공한다(앱_로그인("manager", "password"));
        토큰_발급에_성공한다(웹_로그인("admin", "password"));
    }

    @DisplayName("아아디/비밀번호가 일치하지 않으면 로그인에 실패한다.")
    @Test
    void login_Fail() {
        인증에_실패한다(앱_로그인("manager", "password12"));
        인증에_실패한다(앱_로그인("manager12", "password"));
    }

    @DisplayName("관리자는 앱에 로그인할 수 없고, 일반 사용자는 웹에 로그인할 수 없다.")
    @Test
    void login_Fail2() {
        권한_검사에_실패한다(앱_로그인("admin", "password"));
        권한_검사에_실패한다(웹_로그인("manager", "password"));
    }

    @DisplayName("리프레쉬 토큰으로 새로운 토큰을 발급받을 수 있다.")
    @Test
    void refresh() {
        String refreshToken = 앱_로그인("manager", "password").jsonPath().getString("refreshToken");

        토큰_발급에_성공한다(토큰_재발급(refreshToken));
    }
}
