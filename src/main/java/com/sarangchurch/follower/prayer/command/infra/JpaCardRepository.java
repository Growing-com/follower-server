package com.sarangchurch.follower.prayer.command.infra;

import com.sarangchurch.follower.prayer.command.domain.model.Card;
import com.sarangchurch.follower.prayer.command.domain.model.Week;
import com.sarangchurch.follower.prayer.command.domain.repository.CardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface JpaCardRepository extends JpaRepository<Card, Long>, CardRepository {
    @Override
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Card> findByMemberIdAndWeek(Long memberId, Week week);
}
