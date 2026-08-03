package com.dev.anh.job.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.admin.model.input.YearMonthData;
import com.dev.anh.job.admin.model.output.DashboardStats;
import com.dev.anh.job.admin.model.service.JobDashboardService;
import com.dev.anh.job.model.output.ModificationResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class JobDashboardController {

	private final JobDashboardService jobDashboardService;
	
	@GetMapping("dashboard/stats")
	ModificationResult<DashboardStats> getDashboardStats() {
		return jobDashboardService.getDashboardStats();
	}
	
	@GetMapping("dashboard/job/years")
	List<Integer> getYears() {
	   return jobDashboardService.getYears();
	}
	
	@GetMapping("dashboard/jobPostingsSummary")
	Map<LocalDate, Long> getJobPostings(YearMonthData data) {
	   return jobDashboardService.getJobPostings(data);
	}	
}