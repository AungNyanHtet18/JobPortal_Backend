package com.dev.anh.job.admin.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Company_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record CompanySearch(
	String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Company> root, Join<Company, Account> account) {

		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
			 param.add(cb.or(
				cb.like(cb.lower(root.get(Company_.description)), ("%").concat(keyword.toLowerCase()).concat("%")),
				cb.like(cb.lower(root.get(Company_.location)), keyword.toLowerCase().concat("%")),
			    cb.like(cb.lower(account.get(Account_.name)), keyword.toLowerCase().concat("%")),
			    cb.like(cb.lower(account.get(Account_.email)), keyword.toLowerCase().concat("%"))
			 ));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}

}
