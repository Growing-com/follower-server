package com.sarangchurch.follower.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarangchurch.follower.auth.application.AuthService;
import com.sarangchurch.follower.auth.application.dto.LoginRequest;
import com.sarangchurch.follower.auth.application.dto.TokenRefreshRequest;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentResponse;
import static com.sarangchurch.follower.docs.DocumentFormatGenerator.getUUIDFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.follower.com", uriPort = 443)
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerDocTest {
    private static final TokenResponse TOKEN_RESPONSE = new TokenResponse(
            "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiTUVNQkVSIiwic3ViIjoiMyIsImlhdCI6MTY2MjcwMDcxNSwiZXhwIjoxNjYyNzYwNzE1fQ.HCSS2ocAF5i380GvIv-MJuEs7J6a6FitGTj29F2XoOIDjloNEDSnryPFODpI1CU3BgfbK9KKN1WosPAHnt51-g",
            UUID.randomUUID().toString());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("로그인 문서화")
    @Test
    void login() throws Exception {
        // given
        given(authService.login(any())).willReturn(TOKEN_RESPONSE);
        LoginRequest request = new LoginRequest("admin", "password");

        // when
        ResultActions result = this.mockMvc.perform(post("/api/auth/login")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-login",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰")
                        )
                ));
    }

    @DisplayName("토큰 재발급 문서화")
    @Test
    void refresh() throws Exception {
        // given
        given(authService.refresh(anyString())).willReturn(TOKEN_RESPONSE);
        TokenRefreshRequest request = new TokenRefreshRequest(UUID.randomUUID().toString());

        // when
        ResultActions result = this.mockMvc.perform(post("/api/auth/refresh")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-refresh",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰").attributes(getUUIDFormat())
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰")
                        )
                ));

    }
}
