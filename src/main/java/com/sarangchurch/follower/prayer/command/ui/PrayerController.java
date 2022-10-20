package com.sarangchurch.follower.prayer.command.ui;

import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.member.command.ui.argumentresolver.AuthMember;
import com.sarangchurch.follower.prayer.command.application.PrayerUpdateService;
import com.sarangchurch.follower.prayer.command.application.dto.request.PrayerUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PrayerController {

    private final PrayerUpdateService prayerUpdateService;

    @PostMapping("/api/my/prayers/respond")
    public void respondPrayers(@AuthMember Member member, @RequestBody @Valid PrayerUpdate request) {
        prayerUpdateService.respondPrayers(member.getId(), request);
    }

}
