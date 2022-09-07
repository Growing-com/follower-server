package com.sarangchurch.follower.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarangchurch.follower.auth.application.RefreshTokenService;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.ui.dto.LoginRequest;
import com.sarangchurch.follower.auth.ui.dto.TokenRefreshRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.follower.com", uriPort = 443)
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerDocTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @DisplayName("로그인 문서화")
    @Test
    void login() throws Exception {
        // given
        given(refreshTokenService.create(anyString())).willReturn(UUID.randomUUID().toString());
        LoginRequest request = new LoginRequest("admin", "password");

        // when
        ResultActions result = this.mockMvc.perform(post("/api/auth/login")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("login",
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
        given(refreshTokenService.refresh(anyString())).willReturn(new TokenResponse(
                "eyJhbGciOiJIUzUxMiJ9...",
                UUID.randomUUID().toString())
        );

        TokenRefreshRequest request = new TokenRefreshRequest(UUID.randomUUID().toString());

        // when
        ResultActions result = this.mockMvc.perform(post("/api/auth/refresh")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("refreshToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰")
                        )
                ));
    }
}
