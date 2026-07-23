package com.dev.anh.job.admin.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.admin.model.input.ApplicationSearch;
import com.dev.anh.job.admin.model.output.ApplicationListItem;
import com.dev.anh.job.admin.model.output.MostAppliedJobListItem;
import com.dev.anh.job.admin.model.service.JobApplyDashboardService;
import com.dev.anh.job.model.output.PageResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class JobApplyDashboardController {

	private final JobApplyDashboardService jobApplyDashboardService;

	@GetMapping("applicationList")
	PageResult<ApplicationListItem> searchApplication(ApplicationSearch applicationSearch, 
			  @RequestParam(required = false, defaultValue = "0") int page) {
		return jobApplyDashboardService.searchApplications(applicationSearch, page, 10); //Specify fixed size to show applicant rows
	}
	
	@GetMapping("mostAppliedJobs")
	List<MostAppliedJobListItem> getMostAppliedJobs() {
		 return jobApplyDashboardService.getMostAppliedJobs();
	}
	
}
