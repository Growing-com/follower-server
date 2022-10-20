package com.sarangchurch.follower.member.query;

import com.sarangchurch.follower.common.types.dto.ApiResponse;
import com.sarangchurch.follower.common.types.dto.OffsetBasedPageRequest;
import com.sarangchurch.follower.member.query.dto.CurrentTeam;
import com.sarangchurch.follower.member.query.dto.MemberDetails;
import com.sarangchurch.follower.member.query.dto.MemberSearchResult;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.ui.argumentresolver.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.sarangchurch.follower.common.types.dto.OffsetBasedPageRequest.*;

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

    @GetMapping("/my/favorites")
    public ApiResponse<List<MemberSearchResult>> myFavorites(@AuthMember Member member) {
        return ApiResponse.of(memberDao.findMemberFavorites(member.getId()));
    }

    @GetMapping("/search")
    public SliceImpl<MemberSearchResult> search(
            @AuthMember Member member,
            @RequestParam String name,
            @ModelAttribute OffsetBasedPageRequest pageRequest
    ) {
        List<MemberSearchResult> content = memberDao.search(member.getDepartmentId(), name, pageRequest);
        boolean hasNext = popIfHasNext(content, pageRequest);
        return new SliceImpl<>(content, pageRequest, hasNext);
    }
}
