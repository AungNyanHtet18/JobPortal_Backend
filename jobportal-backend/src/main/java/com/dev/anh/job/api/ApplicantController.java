package com.dev.anh.job.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.ApplicantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("applicant")
@RequiredArgsConstructor
public class ApplicantController {

	private final ApplicantService service;
	
	@GetMapping
	String hello() {
		 return "hello applicant";
	}
	
	@PostMapping
	ModificationResult<Long> storeApplicantInfo(@RequestBody @Validated ApplicantForm form) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		return service.storeApplicantInfo(username, form);
	}
	
	
	@PutMapping("{id}")
	ModificationResult<Long> updateApplicantInfo(@PathVariable Long id,
					@RequestBody @Validated ApplicantForm form) {
		return service.updateApplicantInfo(id, form);
	}
	
}
