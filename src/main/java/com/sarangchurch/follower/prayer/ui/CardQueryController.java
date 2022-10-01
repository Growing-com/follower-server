package com.sarangchurch.follower.prayer.ui;

import com.sarangchurch.follower.common.ApiResponse;
import com.sarangchurch.follower.prayer.dao.CardDao;
import com.sarangchurch.follower.prayer.dao.dto.CardInfo;
import com.sarangchurch.follower.prayer.domain.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardQueryController {

    private final CardDao cardDao;

    @GetMapping("/api/teams/{teamId}/cards")
    public ApiResponse<List<CardInfo>> findThisWeekCards(
            @PathVariable Long teamId,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date
    ) {
        return ApiResponse.of(cardDao.findCardsByTeamAndWeek(teamId, Week.previousSunday(date)));
    }

}
