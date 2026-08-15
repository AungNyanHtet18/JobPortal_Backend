package com.dev.anh.job.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;
		
	@GetMapping("status/{email}")
	ModificationResult<Boolean> checkRoleStatus(@PathVariable String email) {
		 return accountService.checkRoleStatus(email);
	}
}