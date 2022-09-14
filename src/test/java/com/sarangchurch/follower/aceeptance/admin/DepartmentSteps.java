package com.sarangchurch.follower.aceeptance.admin;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static com.sarangchurch.follower.aceeptance.CommonSteps.givenAdmin;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class DepartmentSteps {

    static ExtractableResponse<Response> 유저를_추가한다(Long departmentId, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "sangwook96");
        params.put("password", "password");
        params.put("name", name);
        params.put("birthDate", "1996-10-16");
        params.put("earlyBorn", "false");
        params.put("gender", "MALE");
        params.put("role", "MEMBER");

        return givenAdmin()
                .accept(APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON_VALUE)
                .body(params)
                .when()
                .post("/api/admin/department/{departmentId}/member", departmentId)
                .then().log().all()
                .extract();
    }

}
