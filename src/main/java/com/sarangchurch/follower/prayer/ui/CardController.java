package com.sarangchurch.follower.prayer.ui;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.ui.AuthMember;
import com.sarangchurch.follower.prayer.application.CardCreateService;
import com.sarangchurch.follower.prayer.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.domain.Week;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

import static java.time.LocalDate.now;

@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardCreateService cardCreateService;

    @PostMapping("/api/prayer/my/cards")
    public void createPrayerCards(@AuthMember Member member, @RequestBody @Valid CardCreate request) {
        validateRequest(request);
        cardCreateService.create(member, request, Week.previousSunday(now()));
    }

    private void validateRequest(CardCreate request) {
        request.getPrayers()
                .stream()
                .filter(it -> Objects.isNull(it.getLinkPrayerId()) && Objects.isNull(it.getContent()))
                .findAny()
                .ifPresent(it -> {
                    throw new IllegalArgumentException("연결 기도 혹은 새 기도를 작성해주세요.");
                });
    }

}
