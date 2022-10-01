package com.sarangchurch.follower.prayer.domain;

import java.util.Optional;

public interface CardRepository {
    <S extends Card> S save(S entity);

    Optional<Card> findByMemberIdAndWeek(Long memberId, Week week);
}
