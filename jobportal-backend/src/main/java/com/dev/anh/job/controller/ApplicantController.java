package com.dev.anh.job.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.input.ApplicantSearch;
import com.dev.anh.job.model.output.ApplicantDetails;
import com.dev.anh.job.model.output.ApplicantListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.service.ApplicantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("applicant")
@RequiredArgsConstructor
public class ApplicantController {

	private final ApplicantService service;
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	@GetMapping
	@PreAuthorize("hasAuthority('CompanyAccount')")
	PageResult<ApplicantListItem> search(ApplicantSearch applicantSearch,
		 			@RequestParam(required = false, defaultValue = "0") int page,
		 			@RequestParam(required = false, defaultValue = "10") int size){
		return service.searchApplicant(applicantSearch, page, size);
	}
	
	
	@GetMapping("{email}")
	ApplicantDetails findByName(@PathVariable String email) {
		return service.findByName(email);
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
	
	@PatchMapping(value="uploadphoto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<String> uploadApplicantProfile(MultipartFile file) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();		
		return service.uploadApplicantProfile(username, uploadPath.concat("/profile"), file);
	}
	
	@PatchMapping(value="uploadresume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<String> uploadApplicantResume(MultipartFile file) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 return service.uploadApplicantResume(username, uploadPath.concat("/resume"), file);
	}
	
	@GetMapping("resume/{id}/download")
	ResponseEntity<Resource>  downloadApplicantResume(@PathVariable Long id) throws IOException {
		return service.downloadApplicantResume(id);
	}
	
}
