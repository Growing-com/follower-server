package com.sarangchurch.follower.prayer.application.doubles;

import com.sarangchurch.follower.prayer.domain.model.Prayer;
import com.sarangchurch.follower.prayer.domain.repository.PrayerRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryPrayerRepository implements PrayerRepository {

    private final Map<Long, Prayer> store = new HashMap<>();
    private long sequence = 0L;

    @Override
    public <S extends Prayer> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);

        List<S> prayers = new ArrayList<>();
        for (S entity : entities) {
            prayers.add(entity);
        }
        return prayers;
    }

    private <S extends Prayer> S save(S entity) {
        if (entity.getId() == null) {
            sequence = sequence + 1;
            ReflectionTestUtils.setField(entity, "id", sequence);
            store.put(sequence, entity);
        }
        return entity;
    }

    @Override
    public List<Prayer> findByIdIn(List<Long> ids) {
        return store.values()
                .stream()
                .filter(it -> ids.contains(it.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByInitialCardId(Long cardId) {
        List<Long> deleteIds = store.values()
                .stream()
                .filter(it -> ReflectionTestUtils.getField(it, "initialCardId").equals(cardId))
                .map(Prayer::getId)
                .collect(Collectors.toList());

        store.entrySet()
                .removeIf(it -> deleteIds.contains(it.getKey()));
    }

    public int size() {
        return store.size();
    }
}
