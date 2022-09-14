package com.sarangchurch.follower.aceeptance.admin;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentAssertions {

    static void 부서에_유저가_추가되었다(ExtractableResponse<Response> response, Long departmentId) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).startsWith("/api/admin/department/" + departmentId);
    }

}
