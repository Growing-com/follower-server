package com.sarangchurch.follower.prayer.ui;

import com.sarangchurch.follower.common.ApiResponse;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.ui.AuthMember;
import com.sarangchurch.follower.prayer.dao.CardDao;
import com.sarangchurch.follower.prayer.dao.dto.CardList;
import com.sarangchurch.follower.prayer.dao.dto.MyCardDetails;
import com.sarangchurch.follower.prayer.domain.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class CardQueryController {

    private final CardDao cardDao;

    @GetMapping("/api/prayers/teams/{teamId}/cards")
    public ApiResponse<List<CardList>> findThisWeekCards(
            @PathVariable Long teamId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date
    ) {
        return ApiResponse.of(cardDao.findCardsByTeamAndWeek(teamId, Week.previousSunday(date)));
    }

    @GetMapping("/api/prayers/my/thisWeekCard")
    public ApiResponse<MyCardDetails> findMyThisWeekCard(@AuthMember Member member) {
         return cardDao.findCardByMemberAndWeek(member.getId(), Week.previousSunday(now()))
                 .map(ApiResponse::of)
                 .orElse(ApiResponse.ofEmpty());
    }

    @GetMapping("/api/prayers/my/latestCard")
    public ApiResponse<MyCardDetails> findMyLatestPastCard(@AuthMember Member member) {
        return cardDao.findLatestPastCardByMember(member.getId())
                .map(ApiResponse::of)
                .orElse(ApiResponse.ofEmpty());
    }
}
