package com.sarangchurch.follower.aceeptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthSteps {

    static ExtractableResponse<Response> 유저_로그인(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/loginApp")
                .then().log().all()
                .extract();
    }

    static ExtractableResponse<Response> 관리자_로그인(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/loginWeb")
                .then().log().all()
                .extract();
    }

    static ExtractableResponse<Response> 토큰_재발급(String refreshToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("refreshToken", refreshToken);

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/refresh")
                .then().log().all()
                .extract();
    }
}
