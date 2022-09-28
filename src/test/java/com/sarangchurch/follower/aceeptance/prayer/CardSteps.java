package com.sarangchurch.follower.aceeptance.prayer;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.sarangchurch.follower.aceeptance.CommonSteps.givenManager;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class CardSteps {

    static List<Object> 기도를_작성한다(String... contents) {
        List<Object> prayers = new ArrayList<>();
        for (String content : contents) {
            prayers.add(Map.of("content", content));
        }
        return prayers;
    }

    static ExtractableResponse<Response> 카드를_작성한다(List<Object> prayers) {
        return givenManager()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(Map.of("prayers", prayers))
                .when()
                .post("/api/prayer/my/cards")
                .then().log().all()
                .extract();
    }
}
