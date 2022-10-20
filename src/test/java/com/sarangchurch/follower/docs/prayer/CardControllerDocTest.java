package com.sarangchurch.follower.docs.prayer;

import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.prayer.command.application.CardCreateService;
import com.sarangchurch.follower.prayer.command.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.command.ui.CardController;
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
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CardControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private CardCreateService cardCreateService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("기도카드 생성 - POST /api/prayer/my/cards")
    @Test
    void createCard() throws Exception {
        // given
        doNothing().when(cardCreateService).create(any(), any(), any());

        CardCreate request = new CardCreate(List.of(
                new CardCreate.PrayerCreate(23L, null),
                new CardCreate.PrayerCreate(null, "밥 잘먹게 해주세요.")
        ));

        // when
        ResultActions result = this.mockMvc.perform(post("/api/prayers/my/cards")
                .header("Authorization", "Bearer " + aToken())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-createCard",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                             fieldWithPath("prayers[0].linkPrayerId").type(NUMBER).description("연결 기도 id(선 처리)").optional(),
                             fieldWithPath("prayers[1].content").type(STRING).description("새 기도 내용(후 처리)").optional()
                        )
                ));
    }
}
