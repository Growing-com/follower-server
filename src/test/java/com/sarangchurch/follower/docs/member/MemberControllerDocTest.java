package com.sarangchurch.follower.docs.member;

import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.member.command.application.MemberService;
import com.sarangchurch.follower.member.command.application.dto.BulkUpdateFavorite;
import com.sarangchurch.follower.member.command.application.dto.ToggleFavorite;
import com.sarangchurch.follower.member.command.ui.MemberController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.sarangchurch.follower.Fixtures.aToken;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerDocTest extends DocTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("즐겨찾기 토글하기 - POST /api/members/my/favorites")
    @Test
    void toggleFavorites() throws Exception {
        // given
        doNothing().when(memberService).toggleFavorites(any(), any());

        // when
        ResultActions result = this.mockMvc.perform(post("/api/members/my/favorites")
                .header("Authorization", "Bearer " + aToken())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ToggleFavorite(1L))));

        // then
        result.andExpect(status().isOk())
                .andDo(document("member-toggleFavorite",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("memberId").type(NUMBER).description("대상 멤버 id")
                        )
                ));
    }

    @DisplayName("즐겨찾기 토글하기 - POST /api/members/my/favorites/bulk")
    @Test
    void bulkUpdateFavorites() throws Exception {
        // given
        doNothing().when(memberService).bulkUpdateFavorites(any(), any());

        // when
        ResultActions result = this.mockMvc.perform(post("/api/members/my/favorites/bulk")
                .header("Authorization", "Bearer " + aToken())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BulkUpdateFavorite(
                        List.of(1L),
                        List.of(2L)
                ))));

        // then
        result.andExpect(status().isOk())
                .andDo(document("member-bulkUpdateFavorites",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("add").type(ARRAY).description("추가 멤버 id 목록"),
                                fieldWithPath("remove").type(ARRAY).description("제거 멤버 id 목록")
                        )
                ));
    }
}
