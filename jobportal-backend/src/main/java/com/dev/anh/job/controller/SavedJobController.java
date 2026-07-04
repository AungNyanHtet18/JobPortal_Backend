package com.dev.anh.job.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.SavedJobListItem;
import com.dev.anh.job.model.service.SavedJobService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class SavedJobController {

	private final SavedJobService savedJobService;
	
	@GetMapping("saved/{jobId}")
	ModificationResult<Long> savedJob(@PathVariable @NotNull(message = "Job Id is required") long jobId) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return savedJobService.savedJob(username, jobId);
	}
	
	@GetMapping("unsaved/{jobId}")
	ModificationResult<Long> unsavedJob(@PathVariable @NotNull(message = "Job Id is required") long jobId) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return savedJobService.unsavedJob(username, jobId);
	}
	
	
	@GetMapping("savedjoblist")
	ModificationResult<List<SavedJobListItem>> savedJobList() {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
    	return savedJobService.savedJobList(username);
	}
	
}
