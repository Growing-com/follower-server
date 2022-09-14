package com.sarangchurch.follower.aceeptance.admin;

import com.sarangchurch.follower.aceeptance.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.sarangchurch.follower.aceeptance.CommonAssertions.요청에_실패한다;
import static com.sarangchurch.follower.aceeptance.admin.DepartmentAssertions.부서에_유저가_추가되었다;
import static com.sarangchurch.follower.aceeptance.admin.DepartmentSteps.유저를_추가한다;

class DepartmentAcceptanceTest extends AcceptanceTest {

    @DisplayName("관리자가 생성한 유저는 모두 관리자와 같은 부서 소속이 된다.")
    @Test
    void addMember() {
        부서에_유저가_추가되었다(유저를_추가한다(대학8부.getId(), "우상욱"), 대학8부.getId());
    }

    @DisplayName("같은 부서에 동일한 이름의 유저가 존재할 수 없다.")
    @Test
    void addMember_DuplicateName() {
        부서에_유저가_추가되었다(유저를_추가한다(대학8부.getId(), "우상욱"), 대학8부.getId());
        요청에_실패한다(유저를_추가한다(대학8부.getId(), "우상욱"));
    }
}
