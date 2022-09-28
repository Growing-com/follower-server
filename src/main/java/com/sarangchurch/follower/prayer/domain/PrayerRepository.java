package com.sarangchurch.follower.prayer.domain;

import java.util.List;

public interface PrayerRepository {
    <S extends Prayer> List<S> saveAll(Iterable<S> entities);

    List<Prayer> findByIdIn(List<Long> ids);

    void deleteByInitialCardId(Long cardId);
}
