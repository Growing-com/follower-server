package com.sarangchurch.follower.department.query.dto;

import com.sarangchurch.follower.common.types.vo.PhoneNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@Getter
@RequiredArgsConstructor
public class DepartmentResponse {
    private final Long departmentId;
    private final String name;
    private final DepartmentInformationResponse information;
    private final DepartmentLinksResponse links;

    @Getter
    @RequiredArgsConstructor
    public static class DepartmentInformationResponse {
        private final String ministerName;
        private final PhoneNumber ministerPhoneNumber;
        private final String location;
    }

    @Getter
    @RequiredArgsConstructor
    public static class DepartmentLinksResponse {
        private final URI youtubeLink;
        private final URI instagramLink;
        private final URI churchLink;
    }
}
