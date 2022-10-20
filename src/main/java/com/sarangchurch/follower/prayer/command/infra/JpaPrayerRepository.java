package com.sarangchurch.follower.prayer.command.infra;

import com.sarangchurch.follower.prayer.command.domain.model.Prayer;
import com.sarangchurch.follower.prayer.command.domain.repository.PrayerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPrayerRepository extends JpaRepository<Prayer, Long>, PrayerRepository {
}
