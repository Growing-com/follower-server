package com.sarangchurch.follower.prayer.infra;

import com.sarangchurch.follower.prayer.domain.Card;
import com.sarangchurch.follower.prayer.domain.CardRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCardRepository extends JpaRepository<Card, Long>, CardRepository {
}
