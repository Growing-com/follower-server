package com.sarangchurch.follower.prayer.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@Getter
public class PrayerUpdate {
    @NotEmpty(message = "응답 처리할 기도들을 입력해주세요.")
    private List<Long> prayerIds;

    public PrayerUpdate(List<Long> prayerIds) {
        this.prayerIds = prayerIds;
    }
}
