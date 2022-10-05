package com.sarangchurch.follower.aceeptance.member;

import com.sarangchurch.follower.member.domain.model.Member;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.sarangchurch.follower.aceeptance.CommonSteps.givenJongmin;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class MemberSteps {

    static ExtractableResponse<Response> 즐겨찾기를_토글한다(Member toMember) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", toMember.getId());

        return givenJongmin()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/members/my/favorites")
                .then().log().all()
                .extract();
    }

}
