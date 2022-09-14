package com.sarangchurch.follower;

import com.sarangchurch.follower.auth.domain.RoleType;
import com.sarangchurch.follower.member.domain.Member;
import com.sarangchurch.follower.member.domain.MemberRole;

public class Fixtures {

    public static String aToken() {
        return "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiTUVNQkVSIiwic3ViIjoiMyIsImlhdCI6MTY2MjcwMDcxNSwiZXhwIjoxNjYyNzYwNzE1fQ.HCSS2ocAF5i380GvIv-MJuEs7J6a6FitGTj29F2XoOIDjloNEDSnryPFODpI1CU3BgfbK9KKN1WosPAHnt51-g";
    }

    public static Member anAdmin() {
        return Member.builder()
                .username("admin")
                .password("password")
                .role(MemberRole.of(RoleType.ADMIN))
                .departmentId(1L)
                .build();
    }

    public static Member aManager() {
        return Member.builder()
                .username("manager")
                .password("password")
                .role(MemberRole.of(RoleType.MANAGER))
                .departmentId(1L)
                .build();
    }
}
