package com.sarangchurch.follower.aceeptance.prayer;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_성공한다;
import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_실패한다;
import static com.sarangchurch.follower.aceeptance.prayer.CardSteps.*;
import static com.sarangchurch.follower.aceeptance.prayer.PrayerSteps.기도를_작성한다;

class CardAcceptanceTest extends AcceptanceTest {

    @DisplayName("이번 주 기도 카드를 작성한다.")
    @Test
    void createCard() {
        요청에_성공한다(카드를_작성한다(기도를_작성한다("밥 잘먹게 해주세요", "똥 잘나오게 해주세요")));
    }

    @DisplayName("기도 카드에 댓글을 작성한다. 대댓글의 깊이는 1까지만 가능하다.")
    @Test
    void cardComments() {
        요청에_성공한다(카드를_작성한다(기도를_작성한다("밥 잘먹게 해주세요", "똥 잘나오게 해주세요")));

        요청에_성공한다(카드에_댓글을_작성한다(1L, null, "댓글입니다"));
        요청에_성공한다(카드에_댓글을_작성한다(1L, 1L, "대댓글입니다"));

        요청에_실패한다(카드에_댓글을_작성한다(1L, 2L, "대댓글의 대댓글입니다"));
    }

}
