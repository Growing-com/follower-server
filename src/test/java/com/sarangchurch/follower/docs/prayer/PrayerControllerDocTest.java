package com.sarangchurch.follower.docs.prayer;

import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.prayer.command.application.PrayerUpdateService;
import com.sarangchurch.follower.prayer.command.application.dto.request.PrayerUpdate;
import com.sarangchurch.follower.prayer.command.ui.PrayerController;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PrayerControllerDocTest extends DocTest {
    private MockMvc mockMvc;

    @Mock
    private PrayerUpdateService prayerUpdateService;

    @InjectMocks
    private PrayerController prayerController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(prayerController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("기도 응답 - POST /api/my/prayers/respond")
    @Test
    void respondPrayers() throws Exception {
        // given
        doNothing().when(prayerUpdateService).respondPrayers(any(), any());

        PrayerUpdate request = new PrayerUpdate(List.of(1L));

        // when
        ResultActions result = this.mockMvc.perform(post("/api/my/prayers/respond")
                .header("Authorization", "Bearer " + aToken())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-respond",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestFields(
                             fieldWithPath("prayerIds").type(ARRAY).description("응답 처리할 기도 id 목록")
                        )
                ));
    }
}
