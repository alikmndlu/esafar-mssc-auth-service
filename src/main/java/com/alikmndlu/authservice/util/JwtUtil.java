package com.alikmndlu.authservice.util;

import com.alikmndlu.authservice.dto.UserDto;
import com.alikmndlu.authservice.exception.JwtTokenMalformedException;
import com.alikmndlu.authservice.exception.JwtTokenMissingException;
import com.alikmndlu.authservice.service.AuthenticateService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.token.validity}")
	private long tokenValidity;

	private final AuthenticateService authenticateService;

	public Claims getClaims(final String token) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage() + " => " + e);
		}
		return null;
	}

	public String generateToken(String emailAddress) {
		UserDto user = authenticateService.findUserByEmailAddress(emailAddress);
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + tokenValidity;
		Date exp = new Date(expMillis);
		return Jwts.builder()
				.setSubject(emailAddress)
				.claim("userId", user.getId())
				.setIssuedAt(new Date(nowMillis))
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}

}
