package com.dev.anh.job.admin.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record AccountSearch(
	Role role,
	String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Account> root) {

		var param = new ArrayList<Predicate>();
		
		if(null != role) {
			 param.add(cb.equal(root.get(Account_.role), role));
		}
		
		if(StringUtils.hasLength(keyword)) {
			param.add(cb.or(
			   cb.like(cb.lower(root.get(Account_.name)), keyword.toLowerCase().concat("%")),
			   cb.like(cb.lower(root.get(Account_.email)), keyword.toLowerCase().concat("%"))
			));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}

}
