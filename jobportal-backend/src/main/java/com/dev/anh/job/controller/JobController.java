package com.dev.anh.job.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.dev.anh.job.model.output.JobDetails;
import com.dev.anh.job.model.output.JobListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.service.JobService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class JobController {

	private final JobService jobService;
	
	@GetMapping
	PageResult<JobListItem> search(JobSearch jobSearch,
					@RequestParam(required = false,defaultValue = "0")int page,
					@RequestParam(required = false,defaultValue = "10")int size) {
		return jobService.searchJob(jobSearch, page, size);
	}
	
	@GetMapping("{id}")
	JobDetails findJobById(@PathVariable Long id) {
		return jobService.findById(id);
	}
	
	@GetMapping("company/{companyId}")
	List<JobDetails> findByCompanyId(@PathVariable @NotNull(message = "Company Id is requried") Long companyId ) {
		 return jobService.findByCompanyId(companyId);
	}
	
	@PostMapping
	ModificationResult<Long> createJob(@RequestBody @Validated JobForm form) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return jobService.createJob(username, form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Long> updateJob(@PathVariable Long id,
						@RequestBody @Validated JobForm form) {
		return jobService.updateJob(id, form);
	}
	
	@DeleteMapping("{id}")
	ModificationResult<String> deleteJob(@PathVariable Long id) {
		return jobService.deleteJob(id);
	}
	
}
