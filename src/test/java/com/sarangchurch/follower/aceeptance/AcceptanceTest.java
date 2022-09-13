package com.sarangchurch.follower.aceeptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.sarangchurch.follower.aceeptance.auth.AuthSteps.앱_로그인;
import static com.sarangchurch.follower.aceeptance.auth.AuthSteps.웹_로그인;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @Value("${local.server.port}")
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DataLoader dataLoader;

    String 관리자;
    String 간사;
    String 리더;
    String 조원;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        dataLoader.loadData();

        관리자 = 웹_로그인("admin", "password").jsonPath().getString("accessToken");
        간사 = 앱_로그인("manager", "password").jsonPath().getString("accessToken");
        리더 = 앱_로그인("leader", "password").jsonPath().getString("accessToken");
        조원 = 앱_로그인("member", "password").jsonPath().getString("accessToken");
    }
}
