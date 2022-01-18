package com.alikmndlu.authservice.controller;

import com.alikmndlu.authservice.dto.LoginCredentialsDto;
import com.alikmndlu.authservice.dto.RegisterCredentialsDto;
import com.alikmndlu.authservice.dto.UserDto;
import com.alikmndlu.authservice.service.AuthenticateService;
import com.alikmndlu.authservice.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthRestController {

	private final JwtUtil jwtUtil;

	private final AuthenticateService authenticateService;


	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginCredentialsDto loginDto) throws JsonProcessingException {
		log.info("Login API Called {EmailAddress: {}, Password: {}}", loginDto.getEmailAddress(), loginDto.getPassword());
		boolean isUsersExistence = authenticateService.checkUserExistence(loginDto.getEmailAddress(), loginDto.getPassword());
		log.info("Login Status {{}}", isUsersExistence);
		return isUsersExistence ?
				ResponseEntity.ok().body(Map.of("token", jwtUtil.generateToken(loginDto.getEmailAddress()))) :
				ResponseEntity.notFound().build();
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> register(@RequestBody RegisterCredentialsDto registerDto) {
		log.info("Register API Called {Name: {}, EmailAddress: {}, Password: {}}", registerDto.getName(), registerDto.getEmailAddress(), registerDto.getPassword());
		UserDto createdUser = authenticateService.registerNewUser(registerDto);
		return ResponseEntity.created(URI.create("/api/users/" + createdUser.getId())).body(createdUser);
	}
}
