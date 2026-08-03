package com.dev.anh.job.model.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.entity.PostComment;
import com.dev.anh.job.model.input.PostCommentForm;
import com.dev.anh.job.model.output.CommentListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.PostCommentRepo;
import com.dev.anh.job.model.repo.PostRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostCommentService {

	private final AccountRepo accountRepo;
	private final PostRepo postRepo;
	private final PostCommentRepo postCommentRepo;

	@Transactional
	public ModificationResult<Long> createCommentPost(String username,
			@NotNull(message = "Post Id is required") Long postId, PostCommentForm form) {

		var account = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));

		var post = postRepo.findById(postId)
				.orElseThrow(() -> new BusinessException("Post with %d is not found".formatted(postId)));

		var postComment = new PostComment();
		postComment.setAccount(account);
		postComment.setPost(post);
		postComment.setComment(form.comment());

		postCommentRepo.save(postComment);
		return new ModificationResult<Long>(postId);
	}

	public List<CommentListItem> findCommentPost(Long postId) {
		return postCommentRepo.findCommentListByPostId(postId);
	}

	@Transactional
	public ModificationResult<String> deleteCommentPost(Long id) {
		postCommentRepo.deleteById(id);
		return new ModificationResult<String>("You successfully deleted comment.");
	}
}
