package com.dev.anh.job.admin.controller;

import java.time.LocalDate;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.admin.model.input.YearMonthData;
import com.dev.anh.job.admin.model.service.AccountManagementDashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AccountManagementDashboardController {

	private final AccountManagementDashboardService memberDashboardService;
		
	@GetMapping("dashboard/memberSummary")
	Map<LocalDate, Long> getMemberSummary(YearMonthData data) {
		return memberDashboardService.getMemberSummary(data);
	}
	
	
}