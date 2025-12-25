package com.dev.anh.job.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.model.input.RefreshForm;
import com.dev.anh.job.model.input.SignInForm;
import com.dev.anh.job.model.input.SignUpForm;
import com.dev.anh.job.model.output.TokenResult;
import com.dev.anh.job.model.service.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("token")
@RequiredArgsConstructor
public class TokenController {
	
	private final TokenService tokenService;
	
	@PostMapping("signup")
	TokenResult signUp(@RequestBody @Validated SignUpForm form ) {
		return tokenService.signUp(form);
	}
	
	@PostMapping("signin")
	TokenResult signIn(@RequestBody @Validated SignInForm form ) {
		 return tokenService.signIn(form);
	}
	
	@PostMapping("refresh")
	TokenResult refresh(@RequestBody @Validated RefreshForm form) {
		 return tokenService.refresh(form);
	}
	
}
