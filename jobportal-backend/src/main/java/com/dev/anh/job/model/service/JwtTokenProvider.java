package com.dev.anh.job.model.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.dev.anh.job.model.consts.TokenType;
import com.dev.anh.job.utils.exception.TokenExpirationException;
import com.dev.anh.job.utils.exception.TokenInvalidException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
		return generate(authentication, TokenType.Access);
	}

	public String generateRefreshToken(Authentication authentication) {
		return generate(authentication, TokenType.Refresh);
	}

	public Authentication parseAccessToken(String jwtToken) {
		return parse(jwtToken, TokenType.Access);
	}
	
	public Authentication parseRefreshToken(String jwtToken) {
		return parse(jwtToken, TokenType.Refresh);
	}
	
	
	private String generate(Authentication authentication, TokenType type) {
		var issuseAt = new Date();
		var calendar = Calendar.getInstance();
		calendar.setTime(issuseAt);
		
		if(type == TokenType.Access) {
			 calendar.add(Calendar.MINUTE, accessLife);
		}else if(type == TokenType.Refresh) { 
			calendar.add(Calendar.MINUTE, refreshLife);
		}
		
		return Jwts.builder()
				   .subject(authentication.getName())
				   .claim("role", authentication.getAuthorities().stream()
						   .map(a -> a.getAuthority()).collect(Collectors.joining(",")))
				   .claim("type", type.name())
				   .signWith(secret)
				   .issuer(issuer)
				   .issuedAt(issuseAt)
				   .expiration(calendar.getTime())
				   .compact();
	}
	
	
	private Authentication parse(String jwtToken, TokenType type) {
		
		try {
			var payLoad = Jwts.parser()
							  .verifyWith(secret)
							  .requireIssuer(issuer)
							  .require("type", type.name())
							  .build()
							  .parseSignedClaims(jwtToken).getPayload();
			
			  var username = payLoad.getSubject();
			  
			  var roles = Arrays.stream(payLoad.get("role", String.class)
					  			.split(","))
					  			.map(a -> new SimpleGrantedAuthority(a))
					  			.toList();
			  
			  return UsernamePasswordAuthenticationToken.authenticated(username, null, roles);
		}catch(ExpiredJwtException e) {
			 throw new TokenExpirationException("%s token is expired".formatted(type));
		}catch(JwtException e) {
			 throw new TokenInvalidException("Invalid %s token.".formatted(type), e);
		}
	}

}
