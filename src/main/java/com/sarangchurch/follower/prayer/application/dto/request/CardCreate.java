package com.sarangchurch.follower.prayer.application.dto.request;

import com.sarangchurch.follower.common.EntitySupplier;
import com.sarangchurch.follower.prayer.domain.Prayer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CardCreate {

    @NotEmpty(message = "기도 카드를 채워주세요.")
    @Size(min = 1, max = 100, message = "기도 카드당 1~100개의 기도를 작성할 수 있습니다.")
    @Valid
    private List<PrayerCreate> prayers;

    public CardCreate(List<PrayerCreate> prayers) {
        this.prayers = prayers;
    }

    public List<Long> linkPrayerIds() {
        return prayers.stream()
                .map(PrayerCreate::getLinkPrayerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Getter
    @NoArgsConstructor
    public static class PrayerCreate implements EntitySupplier<Prayer> {

        private Long linkPrayerId;
        @Size(min = 1, max = 3000, message = "기도 하나당 1~3000자를 입력할 수 있습니다.")
        private String content;

        public PrayerCreate(Long linkPrayerId, String content) {
            this.linkPrayerId = linkPrayerId;
            this.content = content;
        }

        @Override
        public Prayer toEntity() {
            return Prayer.builder()
                    .content(content)
                    .responded(false)
                    .build();
        }

    }
}
