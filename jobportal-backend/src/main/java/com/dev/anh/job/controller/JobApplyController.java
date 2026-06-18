package com.dev.anh.job.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.input.ApplicationStatusForm;
import com.dev.anh.job.model.output.ApplicantAppliedJobListItem;
import com.dev.anh.job.model.output.JobApplicationListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.JobApplyService;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("apply")
@RequiredArgsConstructor
public class JobApplyController {

    private final JobApplyService jobApplyService;
	
    @GetMapping("applicantinfo/{jobId}")
    @PreAuthorize("hasAuthority('CompanyAccount')") //checking candidate lists
    ModificationResult<List<JobApplicationListItem>> checkingApplicantList(@PathVariable @NotNull(message = "Job Id is required") long jobId) {
    	 return jobApplyService.checkingApplicantList(jobId);
    }
    
	@PutMapping("updatestatus/{jobId}")
    @PreAuthorize("hasAuthority('CompanyAccount')")
	ModificationResult<Long> updateApplicationStatus(@PathVariable  @NotNull(message = "Job Id is required") long jobId, @RequestBody @Validated ApplicationStatusForm form) {
		return jobApplyService.updateApplicationStatus(jobId, form);
	}
    
    @GetMapping("joblist")
    ModificationResult<List<ApplicantAppliedJobListItem>> checkingAppliedJobList() {
    	var username = SecurityContextHolder.getContext().getAuthentication().getName();
    	return jobApplyService.checkingAppliedJobList(username);
    }
    
	@GetMapping("position/{jobId}") 
	ModificationResult<String> applyJob(@PathVariable @NotNull(message = "Job Id is required") long jobId ) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return jobApplyService.applyJob(username, jobId);
	}
	
	@DeleteMapping("cancel/{jobId}")
	ModificationResult<String> cancelJob(@PathVariable @NotNull(message = "Job Id is required") long jobId ) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return jobApplyService.cancelJob(username, jobId);
	}

}
