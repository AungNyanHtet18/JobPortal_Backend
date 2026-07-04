package com.dev.anh.job.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.entity.Post_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record PostSearch(
	String keyword) {

	public Predicate[] where(Join<Post, Account> account, CriteriaBuilder cb, Root<Post> root) {
		
		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
		   param.add(cb.or(
			   cb.like(cb.lower(root.get(Post_.content)), "%".concat(keyword.toLowerCase()).concat("%")),
			   cb.like(cb.lower(account.get(Account_.name)), keyword.toLowerCase().concat("%"))
			));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}
}
