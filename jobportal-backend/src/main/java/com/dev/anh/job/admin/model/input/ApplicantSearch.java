package com.dev.anh.job.admin.model.input;

import java.util.ArrayList;
import org.springframework.util.StringUtils;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record ApplicantSearch(
	String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Applicant> root, Join<Applicant, Account> account) {

		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
			param.add(cb.or(
			 cb.like(cb.lower(root.get(Applicant_.professionalSummary)), ("%").concat(keyword.toLowerCase()).concat("%")),
			 cb.like(cb.lower(root.get(Applicant_.address)), keyword.toLowerCase().concat("%")),
			 cb.like(cb.lower(account.get(Account_.name)), keyword.toLowerCase().concat("%")),
			 cb.like(cb.lower(account.get(Account_.email)), keyword.toLowerCase().concat("%"))
			));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}

}
