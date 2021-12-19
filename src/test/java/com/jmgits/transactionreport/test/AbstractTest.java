package com.jmgits.transactionreport.test;

import com.jmgits.transactionreport.security.resolver.TokenHandler;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SqlGroup(@Sql(
        scripts = "/sql/_clean_up.sql",
        executionPhase = AFTER_TEST_METHOD
))
@SpringBootTest(webEnvironment = RANDOM_PORT)
public abstract class AbstractTest {

    @Autowired
    protected TokenHandler tokenHandler;

    @Autowired
    protected TestRestTemplate restTemplate;
}
