package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.entity.Post_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record PostListItem(
	Long id,
	String content,
	String postPhoto,
	String accountName,
	String accountPhoto) {

	public static void select(Join<Post, Account> account, CriteriaQuery<PostListItem> cq, CriteriaBuilder cb, Root<Post> root) {

		var applicant = account.join(Account_.applicant, JoinType.LEFT);
		var company = account.join(Account_.company, JoinType.LEFT);
		
		var photo = cb.selectCase()
						.when(
							cb.equal(account.get(Account_.role), Role.Applicant), 
							applicant.get(Applicant_.profilePhoto))
						.when(
						    cb.equal(account.get(Account_.role), Role.CompanyAccount), 
						    company.get(Company_.profilePhoto))
						.otherwise((String)null);

		cq.multiselect(
			root.get(Post_.id),
			root.get(Post_.content),
			root.get(Post_.postPhoto),
			root.get(Post_.account).get(Account_.name),
			photo);
		
	}

}
