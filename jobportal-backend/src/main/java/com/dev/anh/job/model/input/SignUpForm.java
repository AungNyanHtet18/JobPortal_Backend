package com.dev.anh.job.model.input;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpForm {

	@NotBlank(message = "Please enter your name.")
	private String username;
	@NotBlank(message = "Please enter email.")
	private  String email;
	@NotBlank(message = "Please enter password.")
	private String password;
	
	
	public Account entity(PasswordEncoder passwordEncoder) {
		var account = new Account();
		account.setEmail(email);
		account.setPassword(passwordEncoder.encode(password));
		account.setActive(true);
		account.setRole(Role.Member);
		account.setActivatedAt(LocalDateTime.now());
		
		return account;
	}
	
	
	public Member entity(Account account) {
		 var member = new Member();
		 member.setId(account.getId());
		 member.setUsername(username);
		
		 member.setAccount(account);
		 
	   return member; 
	}
	
	
	
}
