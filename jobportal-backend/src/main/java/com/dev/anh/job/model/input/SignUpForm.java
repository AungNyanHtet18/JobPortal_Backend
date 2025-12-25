package com.dev.anh.job.model.input;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpForm {

	@NotBlank(message = "Please enter your name.")
	private String name;
	@NotBlank(message = "Please enter email.")
	private  String email;
	@NotBlank(message = "Please enter password.")
	private String password;
	@NotNull(message = "Please select role to create account")
	private Role role;
	
	public Account entity(PasswordEncoder passwordEncoder) {
		var account = new Account();
		account.setUsername(name);
		account.setEmail(email);
		account.setPassword(passwordEncoder.encode(password));
		account.setActive(true);
		account.setRole(role);
		account.setActivatedAt(LocalDateTime.now());
		
		return account;
	}


}
