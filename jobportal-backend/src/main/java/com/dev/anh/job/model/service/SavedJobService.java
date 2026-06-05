package com.dev.anh.job.model.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.entity.SavedJob;
import com.dev.anh.job.model.entity.embeddable.SavedJobPk;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.SavedJobListItem;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.model.repo.JobRepo;
import com.dev.anh.job.model.repo.SavedJobRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedJobService {
	
	private final ApplicantRepo applicantRepo;
	private final JobRepo jobRepo;
	private final SavedJobRepo savedJobRepo;

	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<Long> savedJob(String username, long jobId) {
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Username %s is not found".formatted(username)));
		var job = jobRepo.findById(jobId).orElseThrow(() -> new BusinessException("Job with %s id is not found".formatted(jobId)));
		
		var savedJobPk = new SavedJobPk(applicant.getId(), job.getId());
		var savedJob = new SavedJob();
		
		savedJob.setId(savedJobPk);
		savedJob.setApplicant(applicant);
		savedJob.setJob(job);
		savedJob.setSavedJob(true);
		
		savedJobRepo.save(savedJob);
		
		return new ModificationResult<Long>(jobId);
	}
	
	
	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<Long> unsavedJob(String username, long jobId) {
		
		var savedJob = savedJobRepo.findOneByApplicantandJob(username, jobId).orElseThrow(() -> new BusinessException("Job with %s id  is not found".formatted(jobId)));
		savedJob.setSavedJob(false);
		savedJobRepo.save(savedJob);
		
		return new ModificationResult<Long>(jobId);
	}
	
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<List<SavedJobListItem>> savedJobList(String username) {
			
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Username %s  is not found".formatted(username)));
		var savedJobLists = savedJobRepo.findJobIdsByApplicantandSavedJob(applicant.getId(),true)
									.stream().map(a -> new SavedJobListItem(a)).toList();
		
		
		return new ModificationResult<List<SavedJobListItem>>(savedJobLists);
	}

}
