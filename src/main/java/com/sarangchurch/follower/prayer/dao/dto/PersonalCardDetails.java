package com.sarangchurch.follower.prayer.dao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class PersonalCardDetails {
    private final Long cardId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate week;
    private List<PersonalPrayerDetails> prayers;

    public void setPrayers(List<PersonalPrayerDetails> prayers) {
        this.prayers = prayers;
    }
}
