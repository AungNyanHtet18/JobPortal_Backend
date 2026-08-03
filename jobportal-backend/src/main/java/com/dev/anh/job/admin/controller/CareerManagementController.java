package com.dev.anh.job.admin.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.admin.model.output.CareerListItem;
import com.dev.anh.job.admin.model.service.CareerManagementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class CareerManagementController {

	private final CareerManagementService careerManagementService;
	
	@GetMapping("career")
	List<CareerListItem> getCareers() { 
		return careerManagementService.getCareers();
	}
}
