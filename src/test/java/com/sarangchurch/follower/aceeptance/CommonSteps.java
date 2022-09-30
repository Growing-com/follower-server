package com.sarangchurch.follower.aceeptance;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static com.sarangchurch.follower.aceeptance.DataLoader.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CommonSteps {

    public static RequestSpecification givenAdmin() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", TEST_ADMIN);
        params.put("password", TEST_PASSWORD);

        String accessToken = RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/loginWeb")
                .then().log().all()
                .extract()
                .jsonPath().getString("content.accessToken");

        return RestAssured.given().log().all()
                .auth().oauth2(accessToken);
    }

    public static RequestSpecification givenJongmin() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", TEST_MANAGER);
        params.put("password", TEST_PASSWORD);

        String accessToken = RestAssured.given().log().all()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/auth/loginApp")
                .then().log().all()
                .extract()
                .jsonPath().getString("content.accessToken");

        return RestAssured.given().log().all()
                .auth().oauth2(accessToken);
    }

}
