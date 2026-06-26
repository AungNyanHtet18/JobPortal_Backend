package com.dev.anh.job.model.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dev.anh.job.event.PostPhotoEvent;
import com.dev.anh.job.model.entity.Post;
import com.dev.anh.job.model.input.PostForm;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.PostRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly =  true)
public class PostService {

	private final AccountRepo accountRepo;
	private final PostRepo postRepo;
	private final ApplicationEventPublisher eventPublisher;
	
	@Transactional
	public ModificationResult<Long> createPost(String username, PostForm form, MultipartFile file) {

			var account = accountRepo.findOneByEmail(username)
					.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
	
			var post = new Post();
			
			post.setAccount(account);
			post.setContent(form.content());
			
			postRepo.save(post);
			
			 // trigger file upload AFTER commit
			if(file != null && !file.isEmpty()) {
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
			if(file != null && !file.isEmpty()) {
				eventPublisher.publishEvent(new PostPhotoEvent(post.getId(), username, file));
			}
					
			return new ModificationResult<Long>(id);
	} 

}
