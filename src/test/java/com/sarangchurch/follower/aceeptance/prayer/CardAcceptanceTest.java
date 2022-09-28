package com.sarangchurch.follower.aceeptance.prayer;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_성공한다;
import static com.sarangchurch.follower.aceeptance.prayer.CardSteps.기도를_작성한다;
import static com.sarangchurch.follower.aceeptance.prayer.CardSteps.카드를_작성한다;

class CardAcceptanceTest extends AcceptanceTest {

    @DisplayName("이번 주 기도 카드를 작성한다.")
    @Test
    void createCard() {
        요청에_성공한다(카드를_작성한다(기도를_작성한다("밥 잘먹게 해주세요", "똥 잘나오게 해주세요")));
    }


}
