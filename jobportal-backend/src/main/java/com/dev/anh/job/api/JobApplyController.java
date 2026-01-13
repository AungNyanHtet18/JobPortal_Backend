package com.dev.anh.job.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.output.ApplicantListItem;
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
	
    @GetMapping("applicantinfo/{jobid}")
    @PreAuthorize("hasAuthority('CompanyAccount')")
    ModificationResult<List<JobApplicationListItem>> applyingApplicantInfo(@PathVariable @NotNull(message = "Job Id is required") long jobId) {
    	 return jobApplyService.applyingApplicantInfo(jobId);
    }
    
    
	@GetMapping("position/{jobId}")
	ModificationResult<String> applicantApplyJob(@PathVariable @NotNull(message = "Job Id is required") long jobId ) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return jobApplyService.apply(username, jobId);
	}
	
	@DeleteMapping("cancel/{jobId}")
	ModificationResult<String> applicantCancelJob(@PathVariable @NotNull(message = "Job Id is required") long jobId ) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return jobApplyService.cancel(username, jobId);
	}
	
}
