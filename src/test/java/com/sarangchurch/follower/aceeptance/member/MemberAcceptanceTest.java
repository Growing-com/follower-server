package com.sarangchurch.follower.aceeptance.member;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_성공한다;
import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_실패한다;
import static com.sarangchurch.follower.aceeptance.member.MemberSteps.즐겨찾기를_수정한다;
import static com.sarangchurch.follower.aceeptance.member.MemberSteps.즐겨찾기를_토글한다;

class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("다른 멤버를 즐겨찾기에 토글할 수 있다.")
    @Test
    void toggleFavorite() {
        요청에_성공한다(즐겨찾기를_토글한다(이순종목사));
        요청에_성공한다(즐겨찾기를_토글한다(이순종목사));
    }

    @DisplayName("자기 자신을 즐겨찾기에 추가/해지할 수 없다.")
    @Test
    void toggleFavorite_Exception() {
        요청에_실패한다(즐겨찾기를_토글한다(이종민));
    }

    @DisplayName("즐겨찾기를 한 번에 추가+삭제할 수 있다.")
    @Test
    void bulkUpdateFavorites() {
        List<Long> 추가_목록 = List.of(이순종목사.getId());
        List<Long> 제거_목록 = List.of(이순종목사.getId());

        요청에_성공한다(즐겨찾기를_수정한다(추가_목록, 제거_목록));
    }
}
