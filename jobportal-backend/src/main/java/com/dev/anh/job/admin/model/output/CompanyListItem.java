package com.dev.anh.job.admin.model.output;

import java.time.LocalDateTime;

import com.dev.anh.job.model.consts.IndustryType;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record CompanyListItem(
	Long id,
	String name,
	String email,
	Boolean active,
	LocalDateTime activatedAt,
	String profilePhoto,
	IndustryType industryType,
	Long jobPostCount) {

	public static void select(CriteriaQuery<CompanyListItem> cq, CriteriaBuilder cb, Root<Company> root, Join<Company, Account> account) {

		var job = root.join(Company_.jobs, JoinType.LEFT);
		
		cq.multiselect(
			root.get(Company_.id),
			account.get(Account_.name),
			account.get(Account_.email),
			account.get(Account_.active),
			account.get(Account_.activatedAt),
			root.get(Company_.profilePhoto),
			root.get(Company_.industryType),
			cb.count(job.get(Job_.id))
		);
		
		cq.groupBy(
			root.get(Company_.id),
			account.get(Account_.name),
			account.get(Account_.email),
			account.get(Account_.active),
			account.get(Account_.activatedAt),
			root.get(Company_.profilePhoto),
			root.get(Company_.industryType)	
		);
		
		
		
	}

}
