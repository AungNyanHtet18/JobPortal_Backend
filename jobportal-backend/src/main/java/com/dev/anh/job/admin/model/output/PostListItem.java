package com.dev.anh.job.admin.model.output;

import java.time.LocalDateTime;

import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.entity.PostComment_;
import com.dev.anh.job.model.entity.PostReact_;
import com.dev.anh.job.model.entity.Post_;
import com.dev.anh.job.model.entity.embeddable.PostReactPk_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record PostListItem(
	Long id,
	String authorName,
	String content,
	Long reactCount,
	Long commentCount,
	LocalDateTime createdAt) {

	public static void select(CriteriaQuery<PostListItem> cq, CriteriaBuilder cb, Root<Post> root) {

		var reactJoin = root.join(Post_.reactPosts, JoinType.LEFT);
		var postJoin = root.join(Post_.postComments, JoinType.LEFT);
		
		cq.multiselect(
		 root.get(Post_.id),
		 root.get(Post_.account).get(Account_.name),
		 root.get(Post_.content),
		 cb.count(reactJoin.get(PostReact_.id).get(PostReactPk_.postId)),
		 cb.count(postJoin.get(PostComment_.id)),
		 root.get(Post_.createdAt));
	}

}
