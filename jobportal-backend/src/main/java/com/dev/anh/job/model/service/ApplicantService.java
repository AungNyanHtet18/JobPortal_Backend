package com.dev.anh.job.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.function.Function;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.input.ApplicantSearch;
import com.dev.anh.job.model.output.ApplicantDetails;
import com.dev.anh.job.model.output.ApplicantListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.utils.FileProvider;
import com.dev.anh.job.utils.exception.BusinessException;
import com.dev.anh.job.utils.exception.FileInvalidException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

	private final AccountRepo accountRepo;
	private final ApplicantRepo applicantRepo;
	private final FileProvider fileProvider;
	
	public PageResult<ApplicantListItem> searchApplicant(ApplicantSearch applicantSearch, int page, int size) {
		return  accountRepo.search(queryFunc(applicantSearch) , countFunc(applicantSearch), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<ApplicantListItem>> queryFunc(ApplicantSearch applicantSearch) {
		return cb -> {
			 var cq = cb.createQuery(ApplicantListItem.class);
			 var root = cq.from(Applicant.class);
			 
			 ApplicantListItem.select(cq, root);
			 cq.where(applicantSearch.where(cb, root));
			 cq.orderBy(cb.desc(root.get(Applicant_.id)));
			 
			return cq;
		};
	}


	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(ApplicantSearch applicantSearch) {
		return cb -> {
			 var cq = cb.createQuery(Long.class);
			 var root = cq.from(Applicant.class);
			 
			 cq.select(cb.count(root.get(Applicant_.id)));
			 cq.where(applicantSearch.where(cb, root));
			 
			 return cq;
			 
		};
	}



	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<Long> storeApplicantInfo(String username, ApplicantForm form) {
		
		var account = accountRepo.findOneByEmail(username)
						.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
		
			 if(StringUtils.hasLength(form.applicantName())) {
				  account.setName(form.applicantName());
				  accountRepo.saveAndFlush(account);
			 }
		
			 //Convert Skill List to String
			 String skills = String.join(",", form.skills());
			  
			 applicantRepo.save(form.entity(account, skills));
			return  new ModificationResult<Long>(account.getId());
		
		
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant')")
	public ModificationResult<Long> updateApplicantInfo(Long id, ApplicantForm form) {
		
		var account = accountRepo.findById(id)
						 .orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));
		
		
		var applicant = applicantRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Applicant with %s id is nod found".formatted(id)));
		
		 if(StringUtils.hasLength(form.applicantName())) {
			  account.setName(form.applicantName());
			  accountRepo.saveAndFlush(account);
		 }
			
		 //Convert Skill List to String
		 String skills = String.join(",", form.skills());
		 
		applicant.setAccount(account);
		applicant.setGender(form.gender());
		applicant.setHighestEducationalAttainment(form.highestEducationalAttainment());
		applicant.setResume(form.resume());
		applicant.setSkills(skills);
		applicant.setProfessionalSummary(form.professionalSummary());
		applicant.setContactDetail(form.contactDetail());
		applicant.setAddress(form.address());
		
	    applicantRepo.save(applicant);

		return new ModificationResult<Long>(id);
	}

	public ApplicantDetails findById(Long id) {
		return applicantRepo.findById(id).map(a -> ApplicantDetails.from(a)).orElseThrow(() -> new BusinessException("Applicant with %d is not found".formatted(id)));
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<String> uploadApplicantProfile(String username, String uploadPath, MultipartFile file) {
		
		fileProvider.validateFile(file, Set.of("png", "jpg", "jpeg")); //validating file
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Firstly,fill applicant infomation before uploading profile image "));
			
		try {
			var profileImageName = fileProvider.generateFileName(applicant.getAccount().getName(), file); 
			var profileImagePath = Path.of(uploadPath, profileImageName);
		
			if(!Files.exists(profileImagePath.getParent())) {
				 Files.createDirectories(profileImagePath.getParent()); //Create directory to save the images
			}
			
			//Save the file 
			Files.copy(file.getInputStream(), profileImagePath, StandardCopyOption.REPLACE_EXISTING);
			applicant.setProfilePhoto(profileImageName);
			
			return new ModificationResult<String>("Successfully Uploaded Profile Photo" + profileImageName);
			
		} catch (IOException e) {
			throw new FileInvalidException("Invalid Profile Upload", e);
		}
		
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<String> uploadApplicantResume(String username, String uploadPath, MultipartFile file) {
		
		fileProvider.validateFile(file, Set.of("pdf","doc","docx"));
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Firstly,fill applicant infomation before uploading profile image "));
		
		try {
			var resumeName = fileProvider.generateFileName(applicant.getAccount().getName(), file);
			var resumePath = Path.of(uploadPath, resumeName);
		
		if(!Files.exists(resumePath.getParent())) { //resumePath.getParent() => C:upload/resume == return the parent directory of the file
				Files.createDirectories(resumePath.getParent());
		}
		
		   Files.copy(file.getInputStream(), resumePath, StandardCopyOption.REPLACE_EXISTING);
		   applicant.setResume(resumeName);
		   
		   return new ModificationResult<String>("Succesfully Uploaded Resume"+ resumeName);
		   
		}catch (IOException e) {
			throw new FileInvalidException("Invalid Resume Upload", e);
		}

	}
	
	public ResponseEntity<Resource> downloadApplicantResume(Long id) throws IOException {
		var applicant = applicantRepo.findById(id).orElseThrow(() -> new BusinessException("Applicant with %id is not found".formatted(id)));
	
		var filePath = Path.of("C:/upload/resume",applicant.getResume());
		var contentType = Files.probeContentType(filePath);
		var fileName = Paths.get(applicant.getResume()).getFileName().toString();
		
		var resource = new FileSystemResource(filePath);

		if(!resource.exists()) {
			 throw new FileInvalidException("File Not Foundd");
		}
		
		return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, 
					   "attachment; filename=\"" + fileName + "\"")
					.body(resource);
	}
	
	
}
