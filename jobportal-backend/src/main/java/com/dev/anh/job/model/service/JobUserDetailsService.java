package com.dev.anh.job.model.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.repo.AccountRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobUserDetailsService implements UserDetailsService{

	private final AccountRepo accountRepo;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return accountRepo.findOneByEmail(username)
						  .map(account -> User.builder()
						  .username(username)
						  .password(account.getPassword())
						  .authorities(account.getRole().name())
						  .disabled(isDisabled(account))
						  .build())
						  .orElseThrow(() -> new UsernameNotFoundException(username));
	}
	
	private boolean isDisabled(Account account) {
		
		if(null == account.getActivatedAt()) {
			 return true;
		}
		
		return LocalDateTime.now().isBefore(account.getActivatedAt());
	}
	
}
