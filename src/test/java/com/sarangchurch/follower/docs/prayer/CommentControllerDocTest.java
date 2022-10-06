package com.sarangchurch.follower.docs.prayer;

import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.prayer.application.CommentService;
import com.sarangchurch.follower.prayer.application.dto.request.CommentCreate;
import com.sarangchurch.follower.prayer.ui.CommentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private CommentService cardCreateService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("댓글 생성 - POST /api/prayers/cards/{cardId}/comments")
    @Test
    void createComment() throws Exception {
        // given
        doNothing().when(cardCreateService).createComment(any(), any(), any());

        CommentCreate request = new CommentCreate(1L, "응애");

        // when
        ResultActions result = this.mockMvc.perform(post("/api/prayers/cards/{cardId}/comments", 1L)
                .header("Authorization", "Bearer " + aToken())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-createComment",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                            parameterWithName("cardId").description("카드 id")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                             fieldWithPath("parentId").type(NUMBER).description("부모 댓글 id").optional(),
                             fieldWithPath("content").type(STRING).description("댓글 내용")
                        )
                ));
    }
}
