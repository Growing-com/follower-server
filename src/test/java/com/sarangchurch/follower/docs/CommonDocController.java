package com.sarangchurch.follower.docs;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.common.EnumType;
import com.sarangchurch.follower.member.domain.Gender;
import lombok.Builder;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Profile("test")
@RestController
public class CommonDocController {

    static class ErrorRequest {
        @NotBlank
        @Size(min = 50, message = "최소 50자 이상이어야 합니다.")
        private String request;

        public ErrorRequest() {
        }

        public ErrorRequest(String request) {
            this.request = request;
        }

        public String getRequest() {
            return request;
        }
    }

    @PostMapping("/docs/error")
    public void throwError(@RequestBody @Valid ErrorRequest errorRequest) {
    }

    static class EnumDocs {
        private final Map<String, String> genders;
        private final Map<String, String> roleTypes;

        @Builder
        public EnumDocs(Map<String, String> genders, Map<String, String> roleTypes) {
            this.genders = genders;
            this.roleTypes = roleTypes;
        }

        public Map<String, String> getGenders() {
            return genders;
        }

        public Map<String, String> getRoleTypes() {
            return roleTypes;
        }
    }


    @GetMapping("/docs/enum")
    public EnumDocs findEnums() {
        Map<String, String> genders = getDocs(Gender.values());
        Map<String, String> roleTypes = getDocs(RoleType.values());

        return EnumDocs.builder()
                .genders(genders)
                .roleTypes(roleTypes)
                .build();
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
    }

}
