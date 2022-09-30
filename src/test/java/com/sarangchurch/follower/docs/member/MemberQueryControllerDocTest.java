package com.sarangchurch.follower.docs.member;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.docs.DocTest;
import com.sarangchurch.follower.member.dao.MemberDao;
import com.sarangchurch.follower.member.dao.dto.CurrentTeamInfo;
import com.sarangchurch.follower.member.dao.dto.MemberInfo;
import com.sarangchurch.follower.member.ui.MemberQueryController;
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
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.getDocumentRequest;
import static com.sarangchurch.follower.docs.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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

    @DisplayName("내 정보 가져오기 - GET /api/member/my/info")
    @Test
    void myInfo() throws Exception {
        // given
        given(memberDao.findMemberInfo(any())).willReturn(new MemberInfo("우상욱",
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

    @DisplayName("활성화된 소속팀 가져오기 - GET /api/member/my/team")
    @Test
    void myTeam() throws Exception {
        // given
        given(memberDao.findCurrentTeam(any())).willReturn(List.of(
                new CurrentTeamInfo(1L, "이종민 LBS"),
                new CurrentTeamInfo(2L, "우상욱 LBS")
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
}
