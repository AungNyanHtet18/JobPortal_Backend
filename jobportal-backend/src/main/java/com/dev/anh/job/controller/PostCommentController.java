package com.dev.anh.job.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.input.PostCommentForm;
import com.dev.anh.job.model.output.CommentListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.PostCommentService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostCommentController {

	private final PostCommentService postCommentService;
		
	@PostMapping("comment/create/{postId}")
	ModificationResult<Long> createCommentPost(@PathVariable @NotNull(message = "Post Id is required") Long postId, @RequestBody @Validated PostCommentForm form ) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return postCommentService.createCommentPost(username, postId, form);
	}
	
	@GetMapping("comment/{postId}")
	List<CommentListItem> findCommentPost(@PathVariable @NotNull(message = "Post Id is required") Long postId) {
		return postCommentService.findCommentPost(postId);
	}
	
	@DeleteMapping("comment/{id}")
    ModificationResult<String> deleteCommentPost(@PathVariable Long id) {
		return postCommentService.deleteCommentPost(id);
	}	
}
