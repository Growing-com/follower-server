package com.sarangchurch.follower.aceeptance.prayer;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sarangchurch.follower.aceeptance.CommonSteps.givenJongmin;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class PrayerSteps {

    static List<Object> 기도를_작성한다(String... contents) {
        List<Object> prayers = new ArrayList<>();
        for (String content : contents) {
            prayers.add(Map.of("content", content));
        }
        return prayers;
    }

    static ExtractableResponse<Response> 기도를_응답처리한다(Long... prayerIds) {
        Map<String, Object> params = new HashMap<>();
        params.put("prayerIds", List.of(prayerIds));

        return givenJongmin()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/my/prayers/respond")
                .then().log().all()
                .extract();
    }
}
