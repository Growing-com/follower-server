package com.sarangchurch.follower.docs.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sarangchurch.follower.admin.application.DepartmentService;
import com.sarangchurch.follower.admin.application.dto.AddMemberRequest;
import com.sarangchurch.follower.admin.ui.DepartmentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static com.sarangchurch.follower.Fixtures.aToken;
import static com.sarangchurch.follower.auth.domain.RoleType.MEMBER;
import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentResponse;
import static com.sarangchurch.follower.docs.DocumentFormatGenerator.getDateFormant;
import static com.sarangchurch.follower.member.domain.Gender.MALE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.follower.com", uriPort = 443)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class DepartmentControllerDocTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @DisplayName("부서 유저 등록: POST /api/admin/department/{departmentId}/member")
    @Test
    void addMember() throws Exception {
        // given
        given(departmentService.addMember(any(), anyLong(), any())).willReturn(1L);

        AddMemberRequest request = AddMemberRequest.builder()
                .username("sangwook96")
                .password("password")
                .name("우상욱")
                .birthDate(LocalDate.of(1996, 10, 16))
                .earlyBorn(false)
                .gender(MALE)
                .role(MEMBER)
                .build();

        // when
        ResultActions result = this.mockMvc.perform(post("/api/admin/department/{departmentId}/member", 1L)
                .header("Authorization", "Bearer " + aToken())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isCreated())
                .andDo(document("admin-dpt-addMember",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("departmentId").description("부서 id")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("username").type(STRING).description("아이디"),
                                fieldWithPath("password").type(STRING).description("비밀번호"),
                                fieldWithPath("name").type(STRING).description("실명"),
                                fieldWithPath("birthDate").type(STRING).description("생년월일").attributes(getDateFormant()),
                                fieldWithPath("earlyBorn").type(BOOLEAN).description("빠른년생 여부"),
                                fieldWithPath("gender").type(STRING).description("<<genders,성별>>"),
                                fieldWithPath("role").type(STRING).description("<<roleTypes,권한>>")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("리소스 생성 URI")
                        )
                ));
    }

}
