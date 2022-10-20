package com.sarangchurch.follower.prayer.command.domain.repository;

import com.sarangchurch.follower.prayer.command.domain.model.Card;
import com.sarangchurch.follower.prayer.command.domain.model.Week;

import java.util.Optional;

public interface CardRepository {
    boolean existsById(Long id);

    <S extends Card> S save(S entity);

    Optional<Card> findByMemberIdAndWeek(Long memberId, Week week);
}
