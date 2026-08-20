package com.dev.anh.job.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.AccountFollowService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountFollowController {

	private final AccountFollowService accountFollowService;
	
	@GetMapping("{followingId}/follow")
	ModificationResult<String> follow(@PathVariable @NotNull(message = "Following Account Id is required") Long followingId) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 return accountFollowService.follow(username, followingId);
	}
	
	@DeleteMapping("{followingId}/unfollow")
	ModificationResult<String> unFollow(@PathVariable @NotNull(message = "Following Account Id is required") Long followingId) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return accountFollowService.unFollow(username, followingId);
	}
	
	@GetMapping("{followingId}/checkFollowStatus")
	ModificationResult<Boolean> checkFollowStatus(@PathVariable @NotNull(message = "Following Account Id is required") Long followingId) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return accountFollowService.checkFollowStatus(username, followingId);
	}
}
