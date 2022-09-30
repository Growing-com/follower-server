package com.sarangchurch.follower.docs.common;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.common.EnumType;
import com.sarangchurch.follower.common.exception.GlobalControllerAdvice;
import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.member.domain.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommonDocControllerTest extends DocTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(new CommonDocController())
                .setControllerAdvice(new GlobalControllerAdvice())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void commonError() throws Exception {
        CommonDocController.ErrorRequest request = new CommonDocController.ErrorRequest("error");

        this.mockMvc.perform(post("/docs/error")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andDo(document("common",
                        customResponseFields("custom-response",
                                null,
                                attributes(key("title").value("공통 예외 응답")),
                                fieldWithPath("status").type(NUMBER).description("상태 코드"),
                                fieldWithPath("code").type(STRING).description("에러 코드"),
                                fieldWithPath("message").type(STRING).description("에러 메시지"),
                                fieldWithPath("errors[0].field").type(STRING).description("검증 실패 필드 이름"),
                                fieldWithPath("errors[0].reason").type(STRING).description("검증 실패 이유")
                        )
                ));
    }

    @Test
    void enums() throws Exception {
        this.mockMvc.perform(get("/docs/enum")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("common",
                        customResponseFields("custom-response",
                                beneathPath("genders"),
                                attributes(key("title").value("성별 (Gender)")),
                                enumConvertFieldDescriptor(Gender.values())
                        ),
                        customResponseFields("custom-response",
                                beneathPath("roleTypes"),
                                attributes(key("title").value("권한 (RoleType)")),
                                enumConvertFieldDescriptor(RoleType.values())
                        )
                ));
    }

    private FieldDescriptor[] enumConvertFieldDescriptor(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .map(enumType -> fieldWithPath(enumType.getName()).description(enumType.getDescription()))
                .toArray(FieldDescriptor[]::new);
    }

    public static CustomResponseFieldsSnippet customResponseFields(
            String type,
            PayloadSubsectionExtractor<?> subsectionExtractor,
            Map<String, Object> attributes,
            FieldDescriptor... descriptors
    ) {
        return new CustomResponseFieldsSnippet(type,
                subsectionExtractor,
                Arrays.asList(descriptors),
                attributes,
                true
        );
    }
}
