package com.sarangchurch.follower.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarangchurch.follower.auth.application.AuthService;
import com.sarangchurch.follower.auth.application.dto.TokenResponse;
import com.sarangchurch.follower.auth.domain.LoginMember;
import com.sarangchurch.follower.auth.ui.AuthController;
import com.sarangchurch.follower.auth.ui.dto.LoginRequest;
import com.sarangchurch.follower.auth.ui.dto.TokenRefreshRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static com.sarangchurch.follower.Fixtures.aToken;
import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.ApiDocumentUtils.getDocumentResponse;
import static com.sarangchurch.follower.docs.DocumentFormatGenerator.getUUIDFormat;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.follower.com", uriPort = 443)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class AuthControllerDocTest {
    private static final TokenResponse TOKEN_RESPONSE = new TokenResponse(aToken(), UUID.randomUUID().toString());
    private static final LoginMember EMPTY_LOGIN_MEMBER = LoginMember.builder().build();

    private MockMvc mockMvc;

    private ObjectMapper  objectMapper = new ObjectMapper();;

    @Mock
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("로그인(앱) - POST /api/auth/loginApp")
    @Test
    void loginApp() throws Exception {
        // given
        given(authenticationManager.authenticate(any())).willReturn(
                new UsernamePasswordAuthenticationToken(EMPTY_LOGIN_MEMBER, null, emptyList()));
        given(authService.loginApp(any())).willReturn(TOKEN_RESPONSE);

        LoginRequest request = new LoginRequest("manager", "password");

        // when
        ResultActions result = this.mockMvc.perform(post("/api/auth/loginApp")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-loginApp",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(STRING).description("아이디"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(STRING).description("리프레쉬 토큰")
                        )
                ));
    }

    @DisplayName("로그인(웹) - POST /api/auth/loginWeb")
    @Test
    void loginWeb() throws Exception {
        // given
        given(authenticationManager.authenticate(any())).willReturn(
                new UsernamePasswordAuthenticationToken(EMPTY_LOGIN_MEMBER, null, emptyList()));
        given(authService.loginWeb(any())).willReturn(TOKEN_RESPONSE);

        LoginRequest request = new LoginRequest("admin", "password");

        // when
        ResultActions result = this.mockMvc.perform(post("/api/auth/loginWeb")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("auth-loginWeb",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("username").type(STRING).description("아이디"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(STRING).description("리프레쉬 토큰")
                        )
                ));
    }

    @DisplayName("토큰 재발급 - POST /api/auth/refresh")
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
                                fieldWithPath("refreshToken").type(STRING).description("리프레쉬 토큰").attributes(getUUIDFormat())
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(STRING).description("엑세스 토큰"),
                                fieldWithPath("refreshToken").type(STRING).description("리프레쉬 토큰")
                        )
                ));

    }
}
