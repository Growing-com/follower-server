package com.sarangchurch.follower.prayer.command.application;

import com.sarangchurch.follower.member.command.domain.model.Member;
import com.sarangchurch.follower.prayer.command.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.command.domain.model.Card;
import com.sarangchurch.follower.prayer.command.domain.model.Prayer;
import com.sarangchurch.follower.prayer.command.domain.model.Week;
import com.sarangchurch.follower.prayer.command.domain.repository.CardRepository;
import com.sarangchurch.follower.prayer.command.domain.repository.PrayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardCreateService {
    private final CardRepository cardRepository;
    private final PrayerRepository prayerRepository;

    @Transactional
    public void create(Member member, CardCreate request, Week week) {
        Card card = cardRepository.findByMemberIdAndWeek(member.getId(), week)
                .orElseGet(() -> cardRepository.save(Card.builder()
                        .memberId(member.getId())
                        .departmentId(member.getDepartmentId())
                        .week(week)
                        .build()));

        prayerRepository.deleteByInitialCardId(card.getId());
        card.update(prayerRepository.saveAll(createPrayers(member, request, card)));
    }

    private List<Prayer> createPrayers(Member member, CardCreate request, Card card) {
        List<Prayer> linkPrayers = prayerRepository.findByIdIn(request.linkPrayerIds());
        linkPrayers.forEach(it -> it.validateLinkable(member.getId(), card.getId()));

        return request.getPrayers()
                .stream()
                .map(prayerCreate -> {
                    if (prayerCreate.getLinkPrayerId() != null) {
                        return linkPrayers.stream()
                                .filter(linkPrayer -> linkPrayer.getId().equals(prayerCreate.getLinkPrayerId()))
                                .findAny()
                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기도 입니다."));
                    }
                    return card.prayer(prayerCreate.getContent());
                })
                .collect(Collectors.toList());
    }
}
