package com.dev.anh.job.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.entity.PostReact;
import com.dev.anh.job.model.entity.embeddable.PostReactPk;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.PostRepo;
import com.dev.anh.job.model.repo.PostReactRepo;
import com.dev.anh.job.utils.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostInteractionService {
	
	private final PostReactRepo reactPostRepo;
	private final AccountRepo accountRepo;
	private final PostRepo postRepo;
	
	@Transactional
	public ModificationResult<Long> reactPost(String username, Long postId) {
		var account = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Account with username: %s is not found".formatted(username)));

		var post = postRepo.findById(postId)
			       .orElseThrow(() -> new BusinessException("Post ID: %d is not found".formatted(postId)));

		var postReactPk = new PostReactPk(account.getId(), post.getId());
		var postReact = new PostReact();
		
		postReact.setId(postReactPk);
		postReact.setAccount(account);
		postReact.setPost(post);
		postReact.setReactPost(true);
		
		reactPostRepo.save(postReact);
		
		return new ModificationResult<Long>(postId);
	}

	@Transactional
	public ModificationResult<Long> unreactPost(String username, Long postId) {
		var reactPost = reactPostRepo.findOneByAccountandPostReact(username, postId)
				        .orElseThrow(() -> new BusinessException("Post ID: %d is not found.".formatted(postId)));
		reactPost.setReactPost(false);
		reactPostRepo.save(reactPost);
		
		return new ModificationResult<Long>(postId);
	}
	
}
