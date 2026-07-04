package com.dev.anh.job.controller;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dev.anh.job.model.input.PostForm;
import com.dev.anh.job.model.input.PostSearch;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PostListItem;
import com.dev.anh.job.model.service.PostService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@GetMapping
	List<PostListItem> searchPost(PostSearch postSearch) {
		 return postService.searchPost(postSearch);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long> createPost(
			@RequestPart("form") @Validated PostForm form,
			@RequestPart(value="file", required = false) MultipartFile file) {
	   var username = SecurityContextHolder.getContext().getAuthentication().getName();
	   return postService.createPost(username, form, file); 
	}
	
	@PutMapping(value="{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long> updatePost(
			@PathVariable Long id,
			@RequestPart("form") @Validated PostForm form,
			@RequestPart(value="file", required = false) MultipartFile file) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return postService.updatePost(username, id, form, file); 
	}
}
