package com.sarangchurch.follower.prayer.domain;

import java.util.List;
import java.util.Optional;

public interface PrayerRepository {
    <S extends Prayer> List<S> saveAll(Iterable<S> entities);

    List<Prayer> findByIdIn(List<Long> ids);

    Optional<Prayer> findById(Long id);

    void deleteByInitialCardId(Long cardId);
}
