package com.dev.anh.job.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
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
import com.dev.anh.job.model.entity.CareerRole;
import com.dev.anh.job.model.entity.Education;
import com.dev.anh.job.model.entity.Experience;
import com.dev.anh.job.model.entity.Language;
import com.dev.anh.job.model.entity.Skill;
import com.dev.anh.job.model.entity.SocialLink;
import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.input.ApplicantSearch;
import com.dev.anh.job.model.input.EducationForm;
import com.dev.anh.job.model.input.ExperienceForm;
import com.dev.anh.job.model.input.SocialLinkForm;
import com.dev.anh.job.model.output.ApplicantDetails;
import com.dev.anh.job.model.output.ApplicantListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.model.repo.CareerRoleRepo;
import com.dev.anh.job.model.repo.EducationRepo;
import com.dev.anh.job.model.repo.ExperienceRepo;
import com.dev.anh.job.model.repo.LanguageRepo;
import com.dev.anh.job.model.repo.SkillRepo;
import com.dev.anh.job.model.repo.SocialLinkRepo;
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
	private final ExperienceRepo experienceRepo;
	private final SocialLinkRepo socialLinkRepo;
	private final EducationRepo educationRepo;
	private final CareerRoleRepo careerRoleRepo;
	private final SkillRepo skillRepo;
	private final LanguageRepo languageRepo;
	
	private final FileProvider fileProvider;
	
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	
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
	public ModificationResult<Long> storeApplicantInfo(String username, ApplicantForm form, MultipartFile file) {
		
		var account = accountRepo.findOneByEmail(username)
						.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
		
		//Specify active is true  to display applicant profile
		account.setRoleStatus(true);
		
		if(StringUtils.hasLength(form.applicantName())) {
			  account.setName(form.applicantName());
			  accountRepo.saveAndFlush(account);  
		 }
		
		var applicant = applicantRepo.saveAndFlush(form.entity(account));
		 
		 if(!form.experiences().isEmpty() && form.experiences() != null) {
			List<Experience> experiences = form.experiences().stream().map(experience -> ExperienceForm.ApplicantJobExperience(applicant, experience)).toList();
			experienceRepo.saveAll(experiences);
		 }
		 
		if(!form.socialLinks().isEmpty() && form.socialLinks() != null) {
			 List<SocialLink> socialLinks = form.socialLinks().stream().map(social -> SocialLinkForm.ApplicantSocialLink(applicant, social)).toList();
			 socialLinkRepo.saveAll(socialLinks);		 	 
		}
		
		if(!form.educations().isEmpty() && form.educations() != null ) {
			 List<Education> educations = form.educations().stream().map(education -> EducationForm.ApplicantEducation(applicant, education)).toList();
			 educationRepo.saveAll(educations);
		}
		
		Set<CareerRole> carrerRoleSet = form.careerRoles().stream()
				.map(careerForm -> careerRoleRepo.findOneByRoleName(careerForm.roleName())
				.orElseGet(() -> {
					 var newCareerRole = new CareerRole();
					 newCareerRole.setRoleName(careerForm.roleName());
				return careerRoleRepo.save(newCareerRole);		 
				})
			).collect(Collectors.toSet());
		
		applicant.setCareerRoles(carrerRoleSet);
		
		if(!form.skills().isEmpty() && form.skills() != null) {
			
		  Set<Skill> skillSet = form.skills().stream()
				 .map(skillform -> skillRepo.findOneBySkillNameAndSkillType(skillform.skillName(),skillform.skillType())
				 .orElseGet(() -> {
						var newSkill = new Skill();
						newSkill.setSkillName(skillform.skillName());
						newSkill.setSkillType(skillform.skillType());
					return skillRepo.save(newSkill);
				 	})
				 ).collect(Collectors.toSet());	
		  
		     applicant.setSkills(skillSet);
		 }
		
		if(!form.languages().isEmpty() && form.languages() != null ) {
			Set<Language> languageSet = form.languages().stream()
				.map(languageform -> languageRepo.findOneByLanguageNameAndLanguageLevel(languageform.languageName(),languageform.languageLevel())
				.orElseGet(()-> {
						 var newLanguage = new Language();
						 newLanguage.setLanguageName(languageform.languageName());
						 newLanguage.setLanguageLevel(languageform.languageLevel());
						return languageRepo.save(newLanguage);
					})		
				).collect(Collectors.toSet());
						
			applicant.setLanguages(languageSet);
		}
		
		//Save the Applicant Profile Photo
		if(file != null && !file.isEmpty()) {
			uploadApplicantProfile(username, uploadPath.concat("/profile"), file);
		}
		 		 
		return  new ModificationResult<Long>(account.getId());
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant')")
	public ModificationResult<Long> updateApplicantInfo(Long id, ApplicantForm form, MultipartFile file) {
		
		var account = accountRepo.findById(id)
						 .orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));
		
		
		var applicant = applicantRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Applicant with %s id is not found".formatted(id)));
		
		 if(StringUtils.hasLength(form.applicantName())) {
			  account.setName(form.applicantName());
			  accountRepo.saveAndFlush(account);
		 }
			
			applicant.setAccount(account);
			applicant.setGender(form.gender());
			applicant.setProfessionalSummary(form.professionalSummary());
			applicant.setContactDetail(form.contactDetail());
			applicant.setAddress(form.address());
						
		   if(!form.experiences().isEmpty() && form.experiences()  != null) {
			    experienceRepo.deleteByApplicant(applicant);
			    
			    List<Experience> experiences = form.experiences().stream().map(experience -> ExperienceForm.ApplicantJobExperience(applicant, experience)).toList();
			    experienceRepo.saveAll(experiences);
		   }
		   
		   if(!form.socialLinks().isEmpty() && form.socialLinks() != null) { 
			   socialLinkRepo.deleteByApplicant(applicant);
		   
			   List<SocialLink> socialLinks = form.socialLinks().stream().map(social -> SocialLinkForm.ApplicantSocialLink(applicant, social)).toList();
			   socialLinkRepo.saveAll(socialLinks);
		   }
		    
		   if(!form.educations().isEmpty() && form.educations() != null) {
			    educationRepo.deleteByApplicant(applicant);
		   
			    List<Education> educations = form.educations().stream().map(education -> EducationForm.ApplicantEducation(applicant, education)).toList();
			    educationRepo.saveAll(educations);
		   }
		   
			   Set<CareerRole> careerRoleSet = form.careerRoles().stream()
					   							 .map(careerForm -> careerRoleRepo.findOneByRoleName(careerForm.roleName())
					   							.orElseGet(() -> {
					   								var newCareerRole = new CareerRole();
					   								newCareerRole.setRoleName(careerForm.roleName());
					   							return careerRoleRepo.save(newCareerRole);
					   							  })
					   						    ).collect(Collectors.toSet());
			
			   applicant.setCareerRoles(careerRoleSet);	   							 
		  
		  if(!form.skills().isEmpty() && form.skills() != null) {
			  
			 Set<Skill> skillSet = form.skills().stream()
					 				.map(skillform -> skillRepo.findOneBySkillNameAndSkillType(skillform.skillName(), skillform.skillType())
					 				.orElseGet(() -> {
						 					var newSkill = new Skill();
						 					newSkill.setSkillName(skillform.skillName());
						 					newSkill.setSkillType(skillform.skillType());					 					
						 				return skillRepo.save(newSkill);
					 					})
					 				).collect(Collectors.toSet());
			  
			 applicant.setSkills(skillSet);			  
		  }
		  
		  if(!form.languages().isEmpty() && form.languages() != null) {
			  
			  Set<Language> languageSet = form.languages().stream()
					  					    .map(languageForm -> languageRepo.findOneByLanguageNameAndLanguageLevel(languageForm.languageName(), languageForm.languageLevel())
					  					    .orElseGet(() -> {
						  					    	var newLanguage = new Language();
						  					    	newLanguage.setLanguageName(languageForm.languageName());
						  					    	newLanguage.setLanguageLevel(languageForm.languageLevel());
						  					    return languageRepo.save(newLanguage);
					  					    	})		
					  					    ).collect(Collectors.toSet());
			 applicant.setLanguages(languageSet); 					    
		  }
		  
		  	applicantRepo.save(applicant);
		 
			if(file != null && !file.isEmpty()) {
				uploadApplicantProfile(account.getEmail(), uploadPath.concat("/profile"), file);
			}

		return new ModificationResult<Long>(id);
	}

    public ApplicantDetails findByApplicantId(Long id) { 
		 var applicant = applicantRepo.findById(id).map(ApplicantDetails::from) .orElseThrow(() -> new BusinessException("Applicant with %d is not found".formatted(id)));
		 return applicant;
	}
	
	public ApplicantDetails findByApplicantName(String email) {
		var applicant =  applicantRepo.findByEmail(email).map(ApplicantDetails::from).orElse(null);
		return applicant;
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<String> uploadApplicantProfile(String username, String uploadPath, MultipartFile file) {
		
		fileProvider.validateFile(file, Set.of("png", "jpg", "jpeg")); //validating file
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Firstly,fill applicant infomation before uploading profile image "));
			
		try {
			var profileImageName = fileProvider.saveFile(uploadPath, applicant.getAccount().getName(), file);
			applicant.setProfilePhoto(profileImageName);
			
			return new ModificationResult<String>("Successfully Uploaded Profile Photo" + profileImageName);
			
		} catch (IOException e) {
			throw new FileInvalidException("Invalid Profile Upload", e);
		}
		
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<String> uploadApplicantResume(String username, MultipartFile file) {
		
		String resumeUploadPath = uploadPath.concat("/resume");
		
		fileProvider.validateFile(file, Set.of("pdf","doc","docx"));
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Firstly,fill applicant infomation before uploading profile image "));
		
		try {
			var resumeName = fileProvider.generateFileName(applicant.getAccount().getName(), file);
			var resumePath = Path.of(resumeUploadPath, resumeName);
		
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
