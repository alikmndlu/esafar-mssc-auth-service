package com.alikmndlu.authservice;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        classes = AuthServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AuthServiceApplicationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final Faker faker = new Faker();

    @Test
    void login(){
        ResponseEntity<Map> loginResponse;

        loginResponse = restTemplate.postForEntity(
                "http://localhost:8080/api/auth/login",
                Map.of(
                        "emailAddress", "alikmndlu1@gmail.com",
                        "password", "1234"
                ),
                Map.class
        );
        assertTrue(loginResponse.getStatusCode().is4xxClientError());

        loginResponse = restTemplate.postForEntity(
                "http://localhost:8080/api/auth/login",
                Map.of(
                        "emailAddress", "alikmndlu1@gmail.com",
                        "password", "123"
                ),
                Map.class
        );;
        assertTrue(loginResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void register(){
        ResponseEntity<Map> registerResponse;

        registerResponse = restTemplate.postForEntity(
                "http://localhost:8080/api/auth/register",
                Map.of(
                        "name", faker.name().fullName(),
                        "emailAddress", faker.internet().emailAddress(),
                        "password", faker.internet().password()
                ),
                Map.class
        );
        assertTrue(registerResponse.getStatusCode().is2xxSuccessful());
    }
}
