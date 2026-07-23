package com.dev.anh.job.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.PostInteractionService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostInteractionController {

	private final PostInteractionService postInteractionService;
	
	@GetMapping("react/{postId}")
	ModificationResult<Long> reactPost(@PathVariable @NotNull(message = "Post Id is required") Long postId) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return postInteractionService.reactPost(username, postId);
	}
	
	@GetMapping("unreact/{postId}")
	ModificationResult<Long> unreactPost(@PathVariable @NotNull(message = "Post Id is required") Long postId) {
	    var username = SecurityContextHolder.getContext().getAuthentication().getName(); 
	    return postInteractionService.unreactPost(username, postId);
	}
	
}
