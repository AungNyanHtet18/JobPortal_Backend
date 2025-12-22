package com.dev.anh.job.model.service;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtTokenProvider {

	private SecretKey secret = Jwts.SIG.HS256.key().build();
	
	
	public String generateAccessToken(Authentication authentication) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public String generateRefreshToken(Authentication authentication) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public Authentication parseRefreshToken(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
}
