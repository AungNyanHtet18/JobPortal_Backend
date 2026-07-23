package com.dev.anh.job.admin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.admin.model.input.YearMonthData;
import com.dev.anh.job.admin.model.service.MemberDashboardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin/dashboard")
@RequiredArgsConstructor
public class MemberDashboardController {

	private final MemberDashboardService memberDashboardService;
	
	@GetMapping("member/years")
	List<Integer> getYears() {
		return memberDashboardService.getYear();
	}
	
	@GetMapping("memberSummary")
	Map<LocalDate, Long> getMemberSummary(YearMonthData data) {
		return memberDashboardService.getMemberSummary(data);
	}
}
