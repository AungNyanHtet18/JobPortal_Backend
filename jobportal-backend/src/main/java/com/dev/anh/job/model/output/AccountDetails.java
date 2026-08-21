package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.Account;

public record AccountDetails(
	Long id,
	String name,
	String email,
	com.dev.anh.job.model.consts.Role role) {

	public static AccountDetails from(Account account) {
		 return new AccountDetails(
				 account.getId(), 
				 account.getName(), 
				 account.getEmail(),
				 account.getRole());
	}
	
}
