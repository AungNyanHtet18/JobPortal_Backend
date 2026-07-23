package com.dev.anh.job.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.admin.model.input.YearMonthData;
import com.dev.anh.job.admin.model.service.JobDashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/dashboard")
@RequiredArgsConstructor
public class JobDashboardController {

	private final JobDashboardService jobDashboardService;
	
	@GetMapping("job/years")
	List<Integer> getYears() {
	   return jobDashboardService.getYear();
	}
	
	@GetMapping("jobPostingsSummary")
	Map<LocalDate, Long> getJobPostings(YearMonthData data) {
	   return jobDashboardService.getJobPostings(data);
	}	
}