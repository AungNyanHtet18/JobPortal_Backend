package com.dev.anh.job.admin.model.service;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.admin.model.input.PostSearch;
import com.dev.anh.job.admin.model.output.PostListItem;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.entity.Post_;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.PostRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostManagementService {

	private final PostRepo postRepo;

	public PageResult<PostListItem> searchPost(PostSearch postSearch, int page, int size) {
		return postRepo.search(queryFunc(postSearch), countFunc(postSearch), page, size);
	}

	private Function<CriteriaBuilder, CriteriaQuery<PostListItem>> queryFunc(PostSearch postSearch) {
		return cb -> {
			var cq = cb.createQuery(PostListItem.class);
			var root = cq.from(Post.class);

			PostListItem.select(cq, cb, root);
			cq.where(postSearch.where(cb, root));
			cq.orderBy(cb.desc(root.get(Post_.createdAt)));

			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(PostSearch postSearch) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Post.class);

			cq.select(cb.count(root.get(Post_.id)));
			cq.where(postSearch.where(cb, root));

			return cq;
		};
	}

	@Transactional
	public ModificationResult<String> deletePost(Long id) {
		postRepo.deleteById(id);
		return new ModificationResult<String>("You successfully deleted post.");
	}

}
