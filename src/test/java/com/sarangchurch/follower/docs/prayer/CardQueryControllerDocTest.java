package com.sarangchurch.follower.docs.prayer;

import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.member.domain.model.Gender;
import com.sarangchurch.follower.prayer.dao.CardDao;
import com.sarangchurch.follower.prayer.dao.dto.CardDetails;
import com.sarangchurch.follower.prayer.dao.dto.ChildCommentDetails;
import com.sarangchurch.follower.prayer.dao.dto.CommentDetails;
import com.sarangchurch.follower.prayer.dao.dto.MemberDetails;
import com.sarangchurch.follower.prayer.dao.dto.MyCardDetails;
import com.sarangchurch.follower.prayer.dao.dto.MyPrayerDetails;
import com.sarangchurch.follower.prayer.dao.dto.PersonalCardDetails;
import com.sarangchurch.follower.prayer.dao.dto.PersonalPrayerDetails;
import com.sarangchurch.follower.prayer.dao.dto.PrayerDetails;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.sarangchurch.follower.Fixtures.aToken;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.*;
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

    private static final LocalDateTime UPDATE_TIME = LocalDateTime.of(2022, 9, 27, 3, 3, 3);

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

    @DisplayName("팀 이번 주 카드 목록 조회 - GET /api/prayers/teams/{teamId}/cards")
    @Test
    void findThisWeekCards() throws Exception {
        // given
        given(cardDao.findCardsByTeamAndWeek(any(), any())).willReturn(List.of(
                new CardDetails(
                        1L,
                        UPDATE_TIME,
                        List.of(new PrayerDetails(1L, UPDATE_TIME, 1L, "밥 잘 먹게 해주세요", true, 1L, "이순종", Gender.MALE, LocalDate.of(1991, 11, 11))),
                        new MemberDetails(1L, "이순종", Gender.MALE, LocalDate.of(1991, 11, 11)),
                        Collections.emptyList()
                )
        ));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/prayers/teams/{teamId}/cards", 1)
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
                                fieldWithPath("content[0].updatedAt").description("카드 수정 시간"),
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

    @DisplayName("기도 창고 조회 - GET /api/prayers/members/{memberId}/cards")
    @Test
    void findCardsByMemberAndYear() throws Exception {
        // given
        PersonalCardDetails card = new PersonalCardDetails(1L, LocalDate.of(2022, 9, 26));
        card.setPrayers(List.of(new PersonalPrayerDetails(1L, 2L, 3L, "하하호호", false)));

        given(cardDao.findCardsByMemberAndYear(any(), any(), any())).willReturn(List.of(card));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/prayers/members/{memberId}/cards", 1)
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON)
                .param("year", "2022")
                .param("offset", "0"));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-findCardsByMemberAndYear",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("멤버 id")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestParameters(
                                parameterWithName("year").description("연도"),
                                parameterWithName("offset").description("페이징 offset")
                        ),
                        responseFields(getPagingFieldDescriptors(
                                fieldWithPath("content[0].cardId").description("카드 id"),
                                fieldWithPath("content[0].week").description("카드 수정 시간"),
                                fieldWithPath("content[0].prayers[0].cardId").description("기도가 속한 카드 id"),
                                fieldWithPath("content[0].prayers[0].seq").description("기도 순서 (카드 내에서)"),
                                fieldWithPath("content[0].prayers[0].prayerId").description("기도 id"),
                                fieldWithPath("content[0].prayers[0].content").description("기도 내용"),
                                fieldWithPath("content[0].prayers[0].response").description("기도 응답 여부")
                                ))
                ));
    }

    @DisplayName("내 이번 주 카드 조회 - GET /api/prayers/my/thisWeekCard")
    @Test
    void findMyThisWeekCard() throws Exception {
        // given
        given(cardDao.findCardByMemberAndWeek(any(), any())).willReturn(Optional.of(new MyCardDetails(
                1L,
                UPDATE_TIME,
                List.of(new MyPrayerDetails(1L, UPDATE_TIME, 1L, "밥 잘먹게 해주세요", false))
        )));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/prayers/my/thisWeekCard")
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-findMyThisWeekCard",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content.cardId").description("카드 id"),
                                fieldWithPath("content.updatedAt").description("카드 수정 시간"),
                                fieldWithPath("content.prayers[0].cardId").description("기도가 속한 카드 id"),
                                fieldWithPath("content.prayers[0].seq").description("기도 순서 (카드 내에서)"),
                                fieldWithPath("content.prayers[0].content").description("기도 내용"),
                                fieldWithPath("content.prayers[0].response").description("기도 응답 여부")
                        )
                ));
    }

    @DisplayName("내 최근 카드 조회 - GET /api/prayers/my/latestCard")
    @Test
    void findMyLatestPastCard() throws Exception {
        // given
        given(cardDao.findLatestPastCardByMember(any())).willReturn(Optional.of(new MyCardDetails(
                1L,
                UPDATE_TIME,
                List.of(new MyPrayerDetails(1L, UPDATE_TIME, 1L, "밥 잘먹게 해주세요", false))
        )));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/prayers/my/latestCard")
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-findMyLatestPastCard",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content.cardId").description("카드 id"),
                                fieldWithPath("content.updatedAt").description("카드 수정 시간"),
                                fieldWithPath("content.prayers[0].cardId").description("기도가 속한 카드 id"),
                                fieldWithPath("content.prayers[0].seq").description("기도 순서 (카드 내에서)"),
                                fieldWithPath("content.prayers[0].content").description("기도 내용"),
                                fieldWithPath("content.prayers[0].response").description("기도 응답 여부")
                        )
                ));
    }

    @DisplayName("기도 카드 상세 조회 - GET /api/prayers/cards/{cardId}")
    @Test
    void findCardById() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        MemberDetails memberDetails = new MemberDetails(1L, "이순종", Gender.MALE, LocalDate.of(1991, 3, 3));
        PrayerDetails prayerDetails = new PrayerDetails(1L, now, 1L, "응애", false, 1L, "이순종", Gender.MALE, LocalDate.of(1991, 3, 3));
        ChildCommentDetails childCommentDetails = new ChildCommentDetails(9L, now, 1L, 3L, "응애 대댓글", memberDetails);
        CommentDetails commentDetails = new CommentDetails(3L, now, 1L, 3L, "응애 댓글", memberDetails, List.of(childCommentDetails));

        given(cardDao.findCardById(any())).willReturn(Optional.of(new CardDetails(
                1L,
                now,
                List.of(prayerDetails),
                memberDetails,
                List.of(commentDetails)
        )));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/prayers/cards/{cardId}", 1L)
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andDo(document("prayer-findCardById",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("cardId").description("카드 id")
                        ),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content.cardId").description("카드 id"),
                                fieldWithPath("content.updatedAt").description("카드 수정 시간"),
                                fieldWithPath("content.prayers[0].cardId").description("기도가 속한 카드 id"),
                                fieldWithPath("content.prayers[0].seq").description("기도 순서 (카드 내에서)"),
                                fieldWithPath("content.prayers[0].content").description("기도 내용"),
                                fieldWithPath("content.prayers[0].response").description("기도 응답 여부"),
                                fieldWithPath("content.member.id").description("카드 작성자 id"),
                                fieldWithPath("content.member.name").description("카드 작성자 이름"),
                                fieldWithPath("content.member.gender").description("카드 작성자 성별"),
                                fieldWithPath("content.member.birthDate").description("카드 작성자 생년월일"),
                                fieldWithPath("content.comments[0].commentId").description("댓글 id"),
                                fieldWithPath("content.comments[0].createdAt").description("댓글 생성 시간"),
                                fieldWithPath("content.comments[0].cardId").description("댓글이 소속된 카드 id"),
                                fieldWithPath("content.comments[0].parentId").description("댓글의 부모 댓글 id (최상위 댓글인 경우 자기 자신)"),
                                fieldWithPath("content.comments[0].content").description("댓글 내용"),
                                fieldWithPath("content.comments[0].member.id").description("댓글 작성자 id"),
                                fieldWithPath("content.comments[0].member.name").description("댓글 작성자 이름"),
                                fieldWithPath("content.comments[0].member.gender").description("댓글 작성자 성별"),
                                fieldWithPath("content.comments[0].member.birthDate").description("댓글 작성자 생년월일"),
                                fieldWithPath("content.comments[0].children[0].commentId").description("대댓글 id"),
                                fieldWithPath("content.comments[0].children[0].createdAt").description("대댓글 생성 시간"),
                                fieldWithPath("content.comments[0].children[0].cardId").description("대댓글이 소속된 카드 id"),
                                fieldWithPath("content.comments[0].children[0].parentId").description("대댓글의 부모 댓글 id"),
                                fieldWithPath("content.comments[0].children[0].content").description("대댓글 내용"),
                                fieldWithPath("content.comments[0].children[0].member.id").description("대댓글 작성자 id"),
                                fieldWithPath("content.comments[0].children[0].member.name").description("대댓글 작성자 이름"),
                                fieldWithPath("content.comments[0].children[0].member.gender").description("대댓글 작성자 성별"),
                                fieldWithPath("content.comments[0].children[0].member.birthDate").description("대댓글 작성자 생년월일")
                        )
                ));
    }
}
