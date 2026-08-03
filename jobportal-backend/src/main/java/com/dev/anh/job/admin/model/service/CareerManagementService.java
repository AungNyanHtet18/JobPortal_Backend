package com.dev.anh.job.admin.model.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.admin.model.output.CareerListItem;
import com.dev.anh.job.model.repo.CareerRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CareerManagementService {

	private final CareerRepo careerRepo;
	
	public List<CareerListItem> getCareers() {
		return careerRepo.findAll()
				   .stream()
				   .map(career ->  new CareerListItem(career.getId(), career.getRoleName()))
				   .collect(Collectors.toList());
	}

}
