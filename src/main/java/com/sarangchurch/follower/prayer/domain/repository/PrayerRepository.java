package com.sarangchurch.follower.prayer.domain.repository;

import com.sarangchurch.follower.prayer.domain.model.Prayer;

import java.util.List;

public interface PrayerRepository {
    <S extends Prayer> List<S> saveAll(Iterable<S> entities);

    List<Prayer> findByIdIn(List<Long> ids);

    void deleteByInitialCardId(Long cardId);
}
