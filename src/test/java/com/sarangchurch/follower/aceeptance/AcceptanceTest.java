package com.sarangchurch.follower.aceeptance;

import com.sarangchurch.follower.department.domain.model.Department;
import com.sarangchurch.follower.department.domain.repository.DepartmentRepository;
import com.sarangchurch.follower.member.domain.model.Member;
import com.sarangchurch.follower.member.domain.repository.MemberRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    @Value("${local.server.port}")
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DataLoader dataLoader;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    protected Department 대학8부;
    protected Member 이순종목사;
    protected Member 이종민;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;

        databaseCleanup.execute();
        dataLoader.loadData();

        이순종목사 = memberRepository.findByUsername("admin").get();
        이종민 = memberRepository.findByUsername("manager").get();
        대학8부 = departmentRepository.findById(이순종목사.getDepartmentId()).get();
    }
}
