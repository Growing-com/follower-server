package com.sarangchurch.follower.docs.prayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarangchurch.follower.prayer.application.CardCreateService;
import com.sarangchurch.follower.prayer.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.ui.CardController;
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
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.follower.com", uriPort = 443)
@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class})
class CardControllerDocTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

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
                new CardCreate.PrayerCreate(null, "밥 잘먹게 해주세요."),
                new CardCreate.PrayerCreate(23L, null)
        ));

        // when
        ResultActions result = this.mockMvc.perform(post("/api/prayer/my/cards")
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
                             fieldWithPath("prayers[1].linkPrayerId").type(NUMBER).description("연결 기도 id(선 처리)").optional(),
                             fieldWithPath("prayers[0].content").type(STRING).description("새 기도 내용(후 처리)").optional()
                        )
                ));
    }
}
