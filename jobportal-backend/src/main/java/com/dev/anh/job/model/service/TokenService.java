package com.dev.anh.job.model.service;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.input.RefreshForm;
import com.dev.anh.job.model.input.SignInForm;
import com.dev.anh.job.model.input.SignUpForm;
import com.dev.anh.job.model.output.TokenResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.MemberRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
	
	private final AccountRepo accountRepo;
	private final MemberRepo memberRepo;
	private final AuthenticationManager authManager;	
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Transactional
	public TokenResult signUp(SignUpForm form) {
		var account = accountRepo.save(form.entity(passwordEncoder));
		memberRepo.save(form.entity(account));
		
		var authentication = UsernamePasswordAuthenticationToken.authenticated(
							 account.getEmail(), 
							 null, 
							 List.of((new SimpleGrantedAuthority(account.getRole().name()))));
		
		return getTokenResult(authentication);
	}


	public TokenResult signIn(SignInForm form) {
	   var authentication = authManager.authenticate(form.authentication());
		
	   return getTokenResult(authentication);
	}

	
	
	public TokenResult refresh(RefreshForm form) {
		var authentication = jwtTokenProvider.parseRefreshToken(form.getToken());
		
		return getTokenResult(authentication);
	}
	
	
	
	private TokenResult getTokenResult(Authentication authentication) {
		
		var account = accountRepo.findOneByEmail(authentication.getName())
								 .orElseThrow(() -> new UsernameNotFoundException(authentication.getName()));
		
		
		
		
		return TokenResult.from(account,
						   jwtTokenProvider.generateAccessToken(authentication),
						   jwtTokenProvider.generateRefreshToken(authentication));
	}



	
	
	
	
}
