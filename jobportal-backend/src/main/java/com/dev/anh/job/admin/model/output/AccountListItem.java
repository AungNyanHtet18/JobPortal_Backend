package com.dev.anh.job.admin.model.output;

import java.time.LocalDateTime;
import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record AccountListItem(
	 Long id,
	 String name,
	 String email,
	 Boolean active,
	 Role role,
	 LocalDateTime activatedAt) {

	public static void select(CriteriaQuery<AccountListItem> cq, Root<Account> root) {

		cq.multiselect(
		  root.get(Account_.id),
		  root.get(Account_.name),
		  root.get(Account_.email),
		  root.get(Account_.active),
		  root.get(Account_.role),
		  root.get(Account_.activatedAt));
		
	}

}
