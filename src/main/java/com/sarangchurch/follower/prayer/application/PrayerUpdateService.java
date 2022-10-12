package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.prayer.application.dto.request.PrayerUpdate;
import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrayerUpdateService {

    private final PrayerRepository prayerRepository;

    @Transactional
    public void respondPrayers(Long memberId, PrayerUpdate request) {
        List<Prayer> prayers = prayerRepository.findByIdIn(request.getPrayerIds());
        prayers.forEach(it -> it.respond(memberId));
    }
}
