package com.dev.anh.job.model.service;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProvider {

	private SecretKey secret = Jwts.SIG.HS256.key().build();
	
	@Value("${app.jwt.issuer}")
	private String issuer;
	@Value("${app.jwt.access-life}")
	private int accessLife;
	@Value("${app.jwt.refresh-life}")
	private int refreshLife;
	
	
	public String generateAccessToken(Authentication authentication) {
		return null;
	}
	
	public String generateRefreshToken(Authentication authentication) {
		return null;
	}


	public Authentication parseAccessToken(String jwtToken) {
		
		
		return null;
	}
	
	public Authentication parseRefreshToken(String token) {
		
		
		return null;
	}

	
}
