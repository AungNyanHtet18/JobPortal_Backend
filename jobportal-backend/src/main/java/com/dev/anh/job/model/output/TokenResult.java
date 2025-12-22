package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;

public record TokenResult(
	String name,
	String email,
	Role role,
	String accessToken,
	String refreshToken){

	
	public static TokenResult from(Account account, String accessToken, String refreshToken) {
		return new TokenResult(
					, 
					account.getEmail(), 
					account.getRole(), 
					accessToken, 
					refreshToken);
	}

}
