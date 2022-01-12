package com.alikmndlu.authservice.service.impl;

import com.alikmndlu.authservice.dto.RegisterCredentialsDto;
import com.alikmndlu.authservice.dto.UserDto;
import com.alikmndlu.authservice.service.AuthenticateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

    private final RestTemplate restTemplate;


    @Override
    public boolean checkUserExistence(String emailAddress, String password) {
        ResponseEntity<Boolean> response = restTemplate.postForEntity(
                "http://USER-SERVICE/api/users/check-existence",
                Map.of(
                        "emailAddress", emailAddress,
                        "password", password
                ),
                Boolean.class
        );

        return Boolean.TRUE.equals(response.getBody());
    }

    @Override
    public UserDto registerNewUser(RegisterCredentialsDto registerDto) {
        ResponseEntity<UserDto> response = restTemplate.postForEntity(
                "http://USER-SERVICE/api/users/",
                Map.of(
                        "name", registerDto.getName(),
                        "emailAddress", registerDto.getEmailAddress(),
                        "password", registerDto.getPassword()
                ),
                UserDto.class
        );
        return response.getBody();
    }

    @Override
    public UserDto findUserByEmailAddress(String emailAddress) {
        ResponseEntity<UserDto> response = restTemplate.getForEntity(
                "http://USER-SERVICE/api/users/by-email/" + emailAddress,
                UserDto.class
        );
        return response.getBody();
    }
}
