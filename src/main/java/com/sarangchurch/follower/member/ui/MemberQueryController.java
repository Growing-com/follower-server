package com.sarangchurch.follower.member.ui;

import com.sarangchurch.follower.common.types.ApiResponse;
import com.sarangchurch.follower.member.dao.MemberDao;
import com.sarangchurch.follower.member.dao.dto.CurrentTeam;
import com.sarangchurch.follower.member.dao.dto.MemberDetails;
import com.sarangchurch.follower.member.domain.model.Member;
import com.sarangchurch.follower.member.ui.argumentresolver.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberDao memberDao;

    @GetMapping("/my/info")
    public ApiResponse<MemberDetails> myInfo(@AuthMember Member member) {
        return ApiResponse.of(memberDao.findMemberInfo(member.getId()));
    }

    @GetMapping("/my/team")
    public ApiResponse<List<CurrentTeam>> myTeam(@AuthMember Member member) {
        return ApiResponse.of(memberDao.findCurrentTeam(member.getId()));
    }
}
