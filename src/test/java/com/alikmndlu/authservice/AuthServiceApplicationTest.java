package com.alikmndlu.authservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        classes = AuthServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AuthServiceApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void test() {

        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/login",
                Map.of(
                        "emailAddress", "alikmndlu1@gmail.com",
                        "password", "123"
                ),
                Map.class
        );
        assertTrue(loginResponse.getStatusCode().is2xxSuccessful());

        loginResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/auth/login",
                Map.of(
                        "emailAddress", "alikmndlu1@gmail.com",
                        "password", "1234"
                ),
                Map.class
        );
        assertTrue(loginResponse.getStatusCode().is4xxClientError());
    }
}
