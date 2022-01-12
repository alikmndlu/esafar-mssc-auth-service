package com.alikmndlu.authservice.service;

import com.alikmndlu.authservice.dto.RegisterCredentialsDto;
import com.alikmndlu.authservice.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AuthenticateService {

    boolean checkUserExistence(String emailAddress, String password) throws JsonProcessingException;

    UserDto registerNewUser(RegisterCredentialsDto registerDto);
}
