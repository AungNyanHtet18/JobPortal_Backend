package com.dev.anh.job.model.service;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProvider {

	private SecretKey secret = Jwts.SIG.HS256.key().build();
	
	public String generateAccessToken(Authentication authentication) {
		
		return null;
	}
	
	public String generateRefreshToken(Authentication authentication) {
		
		return null;
	}

	public Authentication parseRefreshToken(String token) {
		
		
		return null;
	}

	
}
