package com.sarangchurch.follower.member.ui;

import com.sarangchurch.follower.member.application.MemberService;
import com.sarangchurch.follower.member.application.dto.BulkUpdateFavorite;
import com.sarangchurch.follower.member.application.dto.ToggleFavorite;
import com.sarangchurch.follower.member.domain.model.Member;
import com.sarangchurch.follower.member.ui.argumentresolver.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/my/favorites")
    public void toggleFavorites(@AuthMember Member member, @RequestBody @Valid ToggleFavorite request) {
        memberService.toggleFavorites(member.getId(), request.getMemberId());
    }

    @PostMapping("/my/favorites/bulk")
    public void bulkUpdateFavorites(@AuthMember Member member, @RequestBody @Valid BulkUpdateFavorite request) {
        memberService.bulkUpdateFavorites(member.getId(), request);
    }
}
