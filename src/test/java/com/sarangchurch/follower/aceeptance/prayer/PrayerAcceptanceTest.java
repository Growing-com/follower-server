package com.sarangchurch.follower.aceeptance.prayer;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_성공한다;
import static com.sarangchurch.follower.aceeptance.prayer.CardSteps.카드를_작성한다;
import static com.sarangchurch.follower.aceeptance.prayer.PrayerSteps.기도를_응답처리한다;
import static com.sarangchurch.follower.aceeptance.prayer.PrayerSteps.기도를_작성한다;

class PrayerAcceptanceTest extends AcceptanceTest {

    @DisplayName("기도를 응답처리 할 수 있다.")
    @Test
    void respondPrayers() {
        요청에_성공한다(카드를_작성한다(기도를_작성한다("밥 잘먹게 해주세요", "똥 잘나오게 해주세요")));
        요청에_성공한다(기도를_응답처리한다(1L, 2L));
    }

}
