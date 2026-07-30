package com.dev.anh.job.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dev.anh.job.model.input.CompanyForm;
import com.dev.anh.job.model.output.CompanyDetails;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.CompanyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;
	
	@GetMapping("id/{id}")
	CompanyDetails findByCompanyId(@PathVariable Long id) {
		 return companyService.findByCompanyId(id);
	}
	
	@GetMapping("{email}")
	CompanyDetails findByCompanyName(@PathVariable String email) {
		 return companyService.findByCompanyName(email);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long>createCompany(
						@RequestPart("form") @Validated CompanyForm form,
						@RequestPart(value ="file", required = false) MultipartFile file) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 return companyService.createCompany(username, form, file);
	}
	
	@PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long>updateCompany(
			@PathVariable Long id,
			@RequestPart("form") @Validated CompanyForm form,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		
		return companyService.updateCompany(id, form, file);		
	}
}
