package com.dev.anh.job.model.input;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInForm {

	@NotBlank(message = "Please enter email for login.")
	private String email;
	@NotBlank(message = "Please enter password for login.")
	private String password;
	
	public Authentication authentication() {
		return UsernamePasswordAuthenticationToken.unauthenticated(email, password);
	}
}
