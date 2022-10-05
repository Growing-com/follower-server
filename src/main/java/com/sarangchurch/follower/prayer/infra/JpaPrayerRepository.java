package com.sarangchurch.follower.prayer.infra;

import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPrayerRepository extends JpaRepository<Prayer, Long>, PrayerRepository {
}
