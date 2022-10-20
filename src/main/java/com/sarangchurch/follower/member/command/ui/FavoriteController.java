package com.sarangchurch.follower.member.command.ui;

import com.sarangchurch.follower.member.command.application.FavoriteService;
import com.sarangchurch.follower.member.command.application.dto.BulkUpdateFavorite;
import com.sarangchurch.follower.member.command.application.dto.ToggleFavorite;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.ui.argumentresolver.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/my/favorites")
    public void toggleFavorites(@AuthMember Member member, @RequestBody @Valid ToggleFavorite request) {
        favoriteService.toggleFavorites(member.getId(), request.getMemberId());
    }

    @PostMapping("/my/favorites/bulk")
    public void bulkUpdateFavorites(@AuthMember Member member, @RequestBody @Valid BulkUpdateFavorite request) {
        favoriteService.bulkUpdateFavorites(member.getId(), request);
    }
}
