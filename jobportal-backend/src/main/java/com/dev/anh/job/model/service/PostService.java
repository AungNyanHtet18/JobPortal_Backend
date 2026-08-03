package com.dev.anh.job.model.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dev.anh.job.event.PostPhotoEvent;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.entity.Post_;
import com.dev.anh.job.model.input.PostForm;
import com.dev.anh.job.model.input.PostSearch;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PostListItem;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.PostRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private final AccountRepo accountRepo;
	private final PostRepo postRepo;
	private final ApplicationEventPublisher eventPublisher;

	public List<PostListItem> searchPost(PostSearch postSearch) {
		return postRepo.search(queryFunc(postSearch));
	}

	private Function<CriteriaBuilder, CriteriaQuery<PostListItem>> queryFunc(PostSearch postSearch) {
		return cb -> {
			var cq = cb.createQuery(PostListItem.class);
			var root = cq.from(Post.class);

			var account = root.join(Post_.account, JoinType.INNER);

			PostListItem.select(account, cq, cb, root, postSearch.username());
			cq.where(postSearch.where(account, cb, root));

			return cq;
		};
	}

	@Transactional
	public ModificationResult<Long> createPost(String username, PostForm form, MultipartFile file) {

		var account = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));

		var post = new Post();

		post.setAccount(account);
		post.setContent(form.content());

		postRepo.save(post);

		// trigger file upload AFTER commit
		if (file != null && !file.isEmpty()) {
			eventPublisher.publishEvent(new PostPhotoEvent(post.getId(), username, file));
		}

		return new ModificationResult<Long>(account.getId());
	}

	@Transactional
	public ModificationResult<Long> updatePost(String username, Long id, PostForm form, MultipartFile file) {

		var post = postRepo.findById(id)
				.orElseThrow(() -> new BusinessException("Post with %d is not found".formatted(id)));

		post.setContent(form.content());

		// trigger file upload AFTER commit
		if (file != null && !file.isEmpty()) {
			eventPublisher.publishEvent(new PostPhotoEvent(post.getId(), username, file));
		}

		return new ModificationResult<Long>(id);
	}

	@Transactional
	public ModificationResult<String> deletePost(Long id) {
		postRepo.deleteById(id);
		return new ModificationResult<String>("You successfully deleted post.");
	}
}
