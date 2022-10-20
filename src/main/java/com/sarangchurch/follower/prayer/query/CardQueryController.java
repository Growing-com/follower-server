package com.sarangchurch.follower.prayer.query;

import com.sarangchurch.follower.common.types.dto.ApiResponse;
import com.sarangchurch.follower.common.types.dto.OffsetBasedPageRequest;
import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.ui.argumentresolver.AuthMember;
import com.sarangchurch.follower.prayer.command.domain.model.Week;
import com.sarangchurch.follower.prayer.query.dto.CardDetails;
import com.sarangchurch.follower.prayer.query.dto.MyCardDetails;
import com.sarangchurch.follower.prayer.query.dto.PersonalCardDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.sarangchurch.follower.common.types.dto.OffsetBasedPageRequest.popIfHasNext;
import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class CardQueryController {

    private final CardDao cardDao;

    @GetMapping("/api/prayers/teams/{teamId}/cards")
    public ApiResponse<List<CardDetails>> findThisWeekCards(
            @PathVariable Long teamId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date
    ) {
        return ApiResponse.of(cardDao.findCardsByTeamAndWeek(teamId, Week.previousSunday(date)));
    }

    @GetMapping("/api/prayers/members/{memberId}/cards")
    public Slice<PersonalCardDetails> findCardsByMemberAndYear(
            @PathVariable Long memberId,
            @RequestParam Integer year,
            @ModelAttribute OffsetBasedPageRequest pageRequest
    ) {
        List<PersonalCardDetails> content = cardDao.findCardsByMemberAndYear(memberId, year, pageRequest);
        boolean hasNext = popIfHasNext(content, pageRequest);
        return new SliceImpl<>(content, pageRequest, hasNext);
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

    @GetMapping("/api/prayers/cards/{cardId}")
    public ApiResponse<CardDetails> findCardById(@PathVariable Long cardId) {
        return cardDao.findCardById(cardId)
                .map(ApiResponse::of)
                .orElse(ApiResponse.ofEmpty());
    }
}
