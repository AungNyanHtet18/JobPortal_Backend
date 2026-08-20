package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.AccountFollow;
import com.dev.anh.job.model.entity.AccountFollow_;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Company_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record AccountFollowListItem(
	Long accountId,
	String accountName,
	String accountPhoto,
	Role accountRole) {

	public static void select(CriteriaQuery<AccountFollowListItem> cq, CriteriaBuilder cb, Root<AccountFollow> root) {
		
		var following = root.join(AccountFollow_.following, JoinType.INNER);
		var applicant = following.join(Account_.applicant, JoinType.LEFT);
		var company = following.join(Account_.company, JoinType.LEFT);
		
		var accountPhoto = cb.selectCase()
				.when(cb.equal(following.get(Account_.role), Role.Applicant), applicant.get(Applicant_.profilePhoto))
				.when(cb.equal(following.get(Account_.role), Role.CompanyAccount), company.get(Company_.profilePhoto))
				.otherwise((String) null);
		
		cq.multiselect(
		  following.get(Account_.id),
		  following.get(Account_.name),
		  accountPhoto,
		  following.get(Account_.role));
	}

}
