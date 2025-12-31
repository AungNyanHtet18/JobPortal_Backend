package com.dev.anh.job.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.input.JobForm;
import com.dev.anh.job.model.input.JobSearch;
import com.dev.anh.job.model.output.JobListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class JobController {

	private final JobService service;
	
	@GetMapping
	PageResult<JobListItem> search(JobSearch jobSearch,
					@RequestParam(required = false,defaultValue = "0")int page,
					@RequestParam(required = false,defaultValue = "10")int size) {
		
		return service.searchJob(jobSearch, page, size);
	}
	
	@PostMapping
	ModificationResult<Long> storeJobInfo(@RequestBody @Validated JobForm form) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return service.storeJobInfo(username, form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Long> updateJobInfo(@PathVariable Long id ,
						@RequestBody @Validated JobForm form) {
		return service.updateJobInfo(id, form);
	}
	
}
