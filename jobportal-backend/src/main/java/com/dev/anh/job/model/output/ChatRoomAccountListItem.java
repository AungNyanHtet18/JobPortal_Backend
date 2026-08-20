package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.ChatRoom;
import com.dev.anh.job.model.entity.Company_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record ChatRoomAccountListItem(
	Long accountId,
	String accountName,
	String accountPhoto,
	Role accountRole) {

	public static void select(CriteriaQuery<ChatRoomAccountListItem> cq, 
							  CriteriaBuilder cb, Root<ChatRoom> root,
							  Join<ChatRoom, Account> senderAccount, 
							  Join<ChatRoom, Account> recipientAccount, 
							  String username) {	
		
		var senderApplicant = senderAccount.join(Account_.applicant, JoinType.LEFT);
		var senderCompany = senderAccount.join(Account_.company, JoinType.LEFT);
		var recipientApplicant = recipientAccount.join(Account_.applicant, JoinType.LEFT);
		var recipientCompany = recipientAccount.join(Account_.company, JoinType.LEFT);
		
		var currentUserIsRecipient = cb.equal(recipientAccount.get(Account_.email), username.toLowerCase());
		var currentUserIsSender = cb.equal(senderAccount.get(Account_.email), username.toLowerCase());
		
		var accountId = cb.<Long>selectCase()
				  		  .when(currentUserIsRecipient, senderAccount.get(Account_.id))
	                	  .when(currentUserIsSender,recipientAccount.get(Account_.id));
		
		var accountName = cb.<String>selectCase()
							.when(currentUserIsRecipient,senderAccount.get(Account_.name))
							.when(currentUserIsSender, recipientAccount.get(Account_.name));
				
		var senderPhoto = cb.<String>selectCase()
							.when(cb.equal(senderAccount.get(Account_.role), Role.Applicant), senderApplicant.get(Applicant_.profilePhoto))
			                .when(cb.equal(senderAccount.get(Account_.role), Role.CompanyAccount), senderCompany.get(Company_.profilePhoto));
		
		var recipientPhoto = cb.<String>selectCase()
				               .when(cb.equal(recipientAccount.get(Account_.role), Role.Applicant), recipientApplicant.get(Applicant_.profilePhoto))
				               .when(cb.equal(recipientAccount.get(Account_.role), Role.CompanyAccount), recipientCompany.get(Company_.profilePhoto));

        var accountPhoto = cb.<String>selectCase()
			        		 .when(currentUserIsRecipient, senderPhoto)
			                 .when(currentUserIsSender, recipientPhoto);
		
		var accountRole = cb.<Role>selectCase()
			    			.when(currentUserIsRecipient, senderAccount.get(Account_.role))
			    			.when(currentUserIsSender, recipientAccount.get(Account_.role));
        
        cq.multiselect(accountId, accountName, accountPhoto, accountRole);
	}

}
