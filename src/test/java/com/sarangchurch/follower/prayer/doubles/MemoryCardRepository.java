package com.sarangchurch.follower.prayer.doubles;

import com.sarangchurch.follower.prayer.domain.Card;
import com.sarangchurch.follower.prayer.domain.CardRepository;
import com.sarangchurch.follower.prayer.domain.Week;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryCardRepository implements CardRepository {

    private final Map<Long, Card> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public <S extends Card> S save(S entity) {
        if (entity.getId() == null) {
            sequence = sequence + 1;
            ReflectionTestUtils.setField(entity, "id", sequence);
            store.put(sequence, entity);
        }
        return entity;
    }

    @Override
    public Optional<Card> findByMemberIdAndWeek(Long memberId, Week week) {
        return store.values()
                .stream()
                .filter(it -> ReflectionTestUtils.getField(it, "memberId").equals(memberId)
                        && ReflectionTestUtils.getField(it, "week").equals(week))
                .findAny();
    }

    public int size() {
        return store.size();
    }
}
