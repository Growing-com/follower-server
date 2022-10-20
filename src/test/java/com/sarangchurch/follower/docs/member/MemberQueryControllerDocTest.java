package com.sarangchurch.follower.docs.member;

import com.sarangchurch.follower.auth.domain.model.RoleType;
import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.member.query.MemberDao;
import com.sarangchurch.follower.member.query.dto.CurrentTeam;
import com.sarangchurch.follower.member.query.dto.MemberDetails;
import com.sarangchurch.follower.member.query.dto.MemberSearchResult;
import com.sarangchurch.follower.member.command.domain.model.Gender;
import com.sarangchurch.follower.member.query.MemberQueryController;
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
import java.util.List;

import static com.sarangchurch.follower.Fixtures.aToken;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberQueryControllerDocTest extends DocTest {

    private MockMvc mockMvc;

    @Mock
    private MemberDao memberDao;

    @InjectMocks
    private MemberQueryController memberQueryController;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();
        mockMvc = MockMvcBuilders.standaloneSetup(memberQueryController)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @DisplayName("내 정보 가져오기 - GET /api/members/my/info")
    @Test
    void myInfo() throws Exception {
        // given
        given(memberDao.findMemberInfo(any())).willReturn(new MemberDetails("우상욱",
                LocalDate.of(1996, 10, 16), "대학 8부", 1L, RoleType.MEMBER));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/members/my/info")
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andDo(document("member-myInfo",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content.name").type(STRING).description("내 이름"),
                                fieldWithPath("content.birthDate").type(STRING).description("생년월일"),
                                fieldWithPath("content.departmentName").type(STRING).description("부서 이름"),
                                fieldWithPath("content.departmentId").type(NUMBER).description("부서 id"),
                                fieldWithPath("content.roleType").type(STRING).description("권한")
                        )
                ));
    }

    @DisplayName("활성화된 소속팀 가져오기 - GET /api/members/my/team")
    @Test
    void myTeam() throws Exception {
        // given
        given(memberDao.findCurrentTeam(any())).willReturn(List.of(
                new CurrentTeam(1L, "이종민 LBS"),
                new CurrentTeam(2L, "우상욱 LBS")
        ));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/members/my/team")
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andDo(document("member-myTeam",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content[0].teamId").type(NUMBER).description("팀 id"),
                                fieldWithPath("content[0].teamName").type(STRING).description("팀 이름")
                        )
                ));
    }

    @DisplayName("내 즐겨찾기 목록 조회 - GET /api/members/my/favorites")
    @Test
    void myFavorites() throws Exception {
        // given
        given(memberDao.findMemberFavorites(any())).willReturn(List.of(
                new MemberSearchResult(1L, "우상욱", LocalDate.now(), Gender.MALE)
        ));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/members/my/favorites")
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andDo(document("member-myFavorites",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        responseFields(
                                fieldWithPath("content[0].memberId").type(NUMBER).description("멤버 id"),
                                fieldWithPath("content[0].name").type(STRING).description("멤버 이름"),
                                fieldWithPath("content[0].birthDate").type(STRING).description("멤버 생년월일"),
                                fieldWithPath("content[0].gender").type(STRING).description("멤버 성별")
                        )
                ));
    }

    @DisplayName("부서 내에서 멤버 검색 by 이름 - GET /api/members/search")
    @Test
    void search() throws Exception {
        // given
        given(memberDao.search(any(), any(), any())).willReturn(List.of(
                new MemberSearchResult(1L, "우상욱", LocalDate.now(), Gender.MALE)
        ));

        // when
        ResultActions result = this.mockMvc.perform(get("/api/members/search")
                .header("Authorization", "Bearer " + aToken())
                .accept(APPLICATION_JSON)
                .param("name", "우")
                .param("offset", "5")
        );

        // then
        result.andExpect(status().isOk())
                .andDo(document("member-search",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰")
                        ),
                        requestParameters(
                                parameterWithName("name").description("검색어"),
                                parameterWithName("offset").description("페이징용 offset (0부터)")
                        ),
                        responseFields(getPagingFieldDescriptors(
                                fieldWithPath("content[0].memberId").type(NUMBER).description("멤버 id"),
                                fieldWithPath("content[0].name").type(STRING).description("멤버 이름"),
                                fieldWithPath("content[0].birthDate").type(STRING).description("멤버 생년월일"),
                                fieldWithPath("content[0].gender").type(STRING).description("멤버 성별"))
                        )
                ));
    }

}
