package com.dev.anh.job.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.admin.model.input.PostSearch;
import com.dev.anh.job.admin.model.output.PostListItem;
import com.dev.anh.job.admin.model.service.PostManagementService;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class PostManagementController {

	private final PostManagementService postManagementService;
	
	@GetMapping("post")
	@PreAuthorize("hasAuthority('Admin')")
	PageResult<PostListItem> searchPost(PostSearch postSearch,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {	 
		return postManagementService.searchPost(postSearch, page, size);
	}
	
	@DeleteMapping("post/{id}")
	ModificationResult<String> deletePost(@PathVariable Long id) {
		 return postManagementService.deletePost(id);
	}
	
	
}
