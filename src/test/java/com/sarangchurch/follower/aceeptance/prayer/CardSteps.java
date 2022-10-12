package com.sarangchurch.follower.aceeptance.prayer;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sarangchurch.follower.aceeptance.CommonSteps.givenJongmin;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class CardSteps {

    static ExtractableResponse<Response> 카드를_작성한다(List<Object> prayers) {
        return givenJongmin()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(Map.of("prayers", prayers))
                .when()
                .post("/api/prayers/my/cards")
                .then().log().all()
                .extract();
    }

    static ExtractableResponse<Response> 카드에_댓글을_작성한다(Long cardId, Long parentId, String content) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("content", content);

        return givenJongmin()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/prayers/cards/{cardId}/comments", cardId)
                .then().log().all()
                .extract();
    }
}
