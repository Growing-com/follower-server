package com.sarangchurch.follower.docs.prayer;

import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.member.domain.Gender;
import com.sarangchurch.follower.prayer.dao.CardDao;
import com.sarangchurch.follower.prayer.dao.dto.CardInfo;
import com.sarangchurch.follower.prayer.dao.dto.MemberInfo;
import com.sarangchurch.follower.prayer.dao.dto.PrayerInfo;
import com.sarangchurch.follower.prayer.ui.CardQueryController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.sarangchurch.follower.Fixtures.aToken;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.getDocumentResponse;
import static com.sarangchurch.follower.docs.utils.DocumentFormatGenerator.getDateFormant;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CardQueryControllerDocTest extends DocTest {

    private MockMvc mockMvc;

    @Mock
    private CardDao cardDao;

    @InjectMocks
    private CardQueryController cardQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.standaloneSetup(cardQueryController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("팀 이번 주 카드 목록 조회 - GET /api/team/{teamId}/cards")
    @Test
    void findThisWeekCards() throws Exception {
        // given
        given(cardDao.findCardsByTeamAndWeek(any(), any())).willReturn(List.of(
                new CardInfo(
                        1L,
                        LocalDateTime.of(2022, 9, 27, 3, 3, 3),
                        List.of(new PrayerInfo(1L, LocalDateTime.of(2022, 9, 27, 3, 3, 3), 1L, "밥 잘 먹게 해주세요", true, 1L, "이순종", Gender.MALE, LocalDate.of(1991, 11, 11))),
                        new MemberInfo(1L, "이순종", Gender.MALE, LocalDate.of(1991, 11, 11))
                )
        ));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/teams/{teamId}/cards", 1)
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON)
                .param("date", "2022-09-25"));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-findThisWeekCards",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("teamId").description("팀 id")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestParameters(
                                parameterWithName("date").description("주차 (일요일 00:00 ~ 토요일 23:59)").attributes(getDateFormant())
                        ),
                        responseFields(
                                fieldWithPath("content[0].cardId").description("카드 id"),
                                fieldWithPath("content[0].updateTime").description("카드 수정 시간"),
                                fieldWithPath("content[0].prayers[0].cardId").description("기도가 속한 카드 id"),
                                fieldWithPath("content[0].prayers[0].seq").description("기도 순서 (카드 내에서)"),
                                fieldWithPath("content[0].prayers[0].content").description("기도 내용"),
                                fieldWithPath("content[0].prayers[0].response").description("기도 응답 여부"),
                                fieldWithPath("content[0].member.id").description("멤버 id"),
                                fieldWithPath("content[0].member.name").description("멤버 이름"),
                                fieldWithPath("content[0].member.gender").description("멤버 성별"),
                                fieldWithPath("content[0].member.birthDate").description("멤버 생일")
                        )
                ));
    }
}
