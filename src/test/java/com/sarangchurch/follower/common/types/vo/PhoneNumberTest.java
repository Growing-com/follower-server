package com.sarangchurch.follower.common.types.vo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void create() {
        assertThatNoException()
                .isThrownBy(() -> new PhoneNumber("010-1234-1234"));
    }

    @Test
    void create_Exception() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new PhoneNumber("01-1234-1234"))
                .withMessage("전화번호 형식이 올바르지 않습니다.");
    }

    @Test
    void equals() {
        assertThat(new PhoneNumber("010-1234-1234")).isEqualTo(new PhoneNumber("010-1234-1234"));
    }
}
