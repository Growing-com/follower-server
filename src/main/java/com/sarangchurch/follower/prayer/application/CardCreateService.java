package com.sarangchurch.follower.prayer.application;

import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.prayer.application.dto.request.CardCreate;
import com.sarangchurch.follower.prayer.domain.Card;
import com.sarangchurch.follower.prayer.domain.CardRepository;
import com.sarangchurch.follower.prayer.domain.Prayer;
import com.sarangchurch.follower.prayer.domain.PrayerRepository;
import com.sarangchurch.follower.prayer.domain.Week;
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

        List<Prayer> linkPrayers = prayerRepository.findByIdIn(request.getLinkPrayerIds());
        linkPrayers.forEach(it -> it.validateLinkable(member.getId(), card.getId()));

        List<Prayer> prayers = request.getPrayers()
                .stream()
                .map(prayerCreate -> {
                    if (prayerCreate.getLinkPrayerId() != null) {
                        return linkPrayers.stream()
                                .filter(linkPrayer -> linkPrayer.getId().equals(prayerCreate.getLinkPrayerId()))
                                .findAny()
                                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기도 입니다."));
                    }
                    return prayerCreate.toEntity()
                            .toMember(member.getId())
                            .toCard(card.getId());
                })
                .collect(Collectors.toList());

        prayerRepository.deleteByInitialCardId(card.getId());
        List<Prayer> savedPrayers = prayerRepository.saveAll(prayers);
        List<Long> prayerIds = savedPrayers.stream()
                .map(Prayer::getId)
                .collect(Collectors.toList());
        card.setPrayers(prayerIds);
    }

}
