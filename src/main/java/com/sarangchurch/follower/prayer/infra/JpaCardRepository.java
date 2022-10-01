package com.sarangchurch.follower.prayer.infra;

import com.sarangchurch.follower.prayer.domain.Card;
import com.sarangchurch.follower.prayer.domain.CardRepository;
import com.sarangchurch.follower.prayer.domain.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface JpaCardRepository extends JpaRepository<Card, Long>, CardRepository {
    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Card> findByMemberIdAndWeek(Long memberId, Week week);
}
