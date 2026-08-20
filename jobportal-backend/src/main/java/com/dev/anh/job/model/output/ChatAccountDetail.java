package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;

public record ChatAccountDetail(
		Long accountId,
		String accountName,
		String accountPhoto,
		Role accountRole) {

	public static ChatAccountDetail from(Account account) {
		
		String accountPhoto = null;
		
		switch(account.getRole()) {
			case Applicant ->  {
				accountPhoto = account.getApplicant().getProfilePhoto();
			}
			
			case CompanyAccount -> {
				accountPhoto = account.getCompany().getProfilePhoto();
			}
			
			default -> {
				 accountPhoto = null;
			}
		}
		
		return new ChatAccountDetail(account.getId(), 
				 					  account.getName(), 
				 					  accountPhoto, 
				 					  account.getRole());
	}
	
	
}
