package com.dev.anh.job.model.output;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dev.anh.job.model.consts.Role;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.entity.PostComment_;
import com.dev.anh.job.model.entity.PostReact;
import com.dev.anh.job.model.entity.PostReact_;
import com.dev.anh.job.model.entity.Post_;
import com.dev.anh.job.model.entity.embeddable.PostReactPk_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record PostListItem(
		Long id, 
		String content,
		String postPhoto, 
		Long accountId,
		String accountName, 
		String accountEmail,
		Role accountRole,
		String accountPhoto, 
		Long reactionCount, 
		Long commentCount, 
		boolean reacted, 
		LocalDateTime createdTime) {

	public static void select(Join<Post, Account> account, CriteriaQuery<PostListItem> cq, CriteriaBuilder cb,
			Root<Post> root, String username) {

		var applicant = account.join(Account_.applicant, JoinType.LEFT);
		var company = account.join(Account_.company, JoinType.LEFT);

		var photo = cb.selectCase()
				.when(cb.equal(account.get(Account_.role), Role.Applicant), applicant.get(Applicant_.profilePhoto))
				.when(cb.equal(account.get(Account_.role), Role.CompanyAccount), company.get(Company_.profilePhoto))
				.otherwise((String) null);

		var reactionJoin = root.join(Post_.reactPosts, JoinType.LEFT);

		reactionJoin.on(cb.equal(reactionJoin.get(PostReact_.reactPost), true));

		var reactionCount = cb.countDistinct(reactionJoin.get(PostReact_.id).get(PostReactPk_.accountId));
		var commentCount = cb.countDistinct(root.join(Post_.postComments, JoinType.LEFT).get(PostComment_.id));

		Expression<Boolean> userReacted;
		Join<Post, PostReact> userReactionJoin = null;

		if (username != null && !username.isEmpty()) {
			userReactionJoin = root.join(Post_.reactPosts, JoinType.LEFT);

			userReactionJoin.on(cb.and(cb.isTrue(userReactionJoin.get(PostReact_.reactPost)),
					cb.equal(userReactionJoin.get(PostReact_.account).get(Account_.email), username)));

			userReacted = cb.isNotNull(userReactionJoin.get(PostReact_.id));

		} else {
			userReacted = cb.literal(false);
		}

		cq.multiselect(
				root.get(Post_.id), 
				root.get(Post_.content), 
				root.get(Post_.postPhoto),
				root.get(Post_.account).get(Account_.id),
				root.get(Post_.account).get(Account_.name), 
				root.get(Post_.account).get(Account_.email), 
				root.get(Post_.account).get(Account_.role),
				photo,
				reactionCount, 
				commentCount, 
				userReacted, 
				root.get(Post_.createdAt));

		// Group By for Join Operations
		List<Expression<?>> groups = new ArrayList<>();
		groups.add(root.get(Post_.id));
		groups.add(root.get(Post_.content));
		groups.add(root.get(Post_.postPhoto));
		groups.add(root.get(Post_.account).get(Account_.id));
		groups.add(root.get(Post_.account).get(Account_.name));
		groups.add(root.get(Post_.account).get(Account_.email));
		groups.add(root.get(Post_.account).get(Account_.role));
		groups.add(photo);
		groups.add(root.get(Post_.createdAt));

		if (userReactionJoin != null) {
			groups.add(userReactionJoin.get(PostReact_.id));
		}

		cq.groupBy(groups);
	}

}
