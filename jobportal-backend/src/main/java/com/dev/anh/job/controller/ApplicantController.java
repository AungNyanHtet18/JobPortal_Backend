package com.dev.anh.job.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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

	private final ApplicantService applicantService;
	
	@GetMapping
	@PreAuthorize("hasAuthority('CompanyAccount')")
	PageResult<ApplicantListItem> searchApplicant(ApplicantSearch applicantSearch,
		 			@RequestParam(required = false, defaultValue = "0") int page,
		 			@RequestParam(required = false, defaultValue = "10") int size){
		return applicantService.searchApplicant(applicantSearch, page, size);
	}
	
	@GetMapping("applicantId/{id}")
	ApplicantDetails findByApplicantId(@PathVariable Long id) {
		 return applicantService.findByApplicantId(id);
	}
		
	@GetMapping("{email}")
    ApplicantDetails findByApplicantName(@PathVariable String email) {
		return applicantService.findByApplicantName(email);
	}
	
	@GetMapping("applicantExists/{email}")
	ModificationResult<Long> findApplicantExists(@PathVariable String email) {
		return applicantService.findApplicantExists(email);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long> createApplicant(
				    @RequestPart("form") @Validated ApplicantForm form,  
					@RequestPart(value = "file", required = false) MultipartFile file) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		return applicantService.createApplicant(username, form, file);
	}
	
	@PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long> updateApplicant(
			          @PathVariable Long id,
			          @RequestPart("form") @Validated ApplicantForm form,
			          @RequestPart(value = "file", required = false) MultipartFile file) {
		return applicantService.updateApplicant(id, form, file);
	}
	
	@PatchMapping(value="uploadresume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<String> uploadApplicantResume(MultipartFile file) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 return applicantService.uploadApplicantResume(username, file);
	}
	
	@PatchMapping(value="uploadcv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<String> uploadApplicantCvForm(MultipartFile file) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		return applicantService.uploadApplicantCvForm(username, file);
	}
	
	@GetMapping("resume/{id}/download")
	@PreAuthorize("hasAuthority('CompanyAccount')")
	ResponseEntity<Resource>  downloadApplicantResume(@PathVariable Long id) throws IOException {
		return applicantService.downloadApplicantResume(id);
	}
	
	@GetMapping("cvForm/{id}/download")
	@PreAuthorize("hasAuthority('CompanyAccount')")
	ResponseEntity<Resource>  downloadApplicantCvForm(@PathVariable Long id) throws IOException {
		return applicantService.downloadApplicantCvForm(id);
	}
	
	
}
