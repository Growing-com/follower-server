package com.sarangchurch.follower.aceeptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthSteps {

    public static ExtractableResponse<Response> 로그인(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        return RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 토큰_재발급(String refreshToken) {
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
