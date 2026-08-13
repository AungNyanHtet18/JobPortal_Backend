package com.dev.anh.job.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.event.JobApplicationEvent;
import com.dev.anh.job.model.consts.ApplicationStatus;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk_;
import com.dev.anh.job.model.input.ApplicationStatusForm;
import com.dev.anh.job.model.output.ApplicantAppliedJobListItem;
import com.dev.anh.job.model.output.JobApplicationListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.model.repo.JobApplyRepo;
import com.dev.anh.job.model.repo.JobRepo;
import com.dev.anh.job.utils.exception.BusinessException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobApplyService {

	private final ApplicantRepo applicantRepo;
	private final JobRepo jobRepo;
	private final JobApplyRepo jobApplyRepo;
	private final ApplicationEventPublisher eventPublisher;

	public ModificationResult<List<JobApplicationListItem>> checkingApplicantList(long jobId) {
		var jobApplicantList = jobRepo.search(queryFuncForApplicantList(jobId));
		return new ModificationResult<List<JobApplicationListItem>>(jobApplicantList);
	}

	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> updateApplicationStatus(long jobId, ApplicationStatusForm form) {
		var jobApply = jobApplyRepo.findByApplicantIdandJobId(form.applicantId(), jobId)
				.orElseThrow(() -> new BusinessException("Applied Job Id: %s is not found".formatted(jobId)));
		jobApply.setNote(form.note());
		jobApply.setStatus(form.status());

		jobApplyRepo.save(jobApply);

		String companyName = Optional.ofNullable(jobApply.getJob().getClientName()).filter(name -> !name.isBlank())
				.orElse(jobApply.getJob().getCompany().getAccount().getName());

		eventPublisher.publishEvent(new JobApplicationEvent(jobApply.getApplicant().getAccount().getEmail(),
				jobApply.getApplicant().getAccount().getName(), form.status(), form.note(), companyName,
				jobApply.getJob().getCareer().getRoleName(), jobApply.getCreatedAt()));

		return new ModificationResult<Long>(jobApply.getId().getJobId());
	}

	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<List<ApplicantAppliedJobListItem>> checkingAppliedJobList(String username) {

		var applicant = applicantRepo.findByEmail(username)
				.orElseThrow(() -> new BusinessException("Username: %s is not found".formatted(username)));
		var appliedJobList = jobRepo.search(queryFuncForJobList(applicant.getId()));

		return new ModificationResult<List<ApplicantAppliedJobListItem>>(appliedJobList);
	}

	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<String> applyJob(String username, long jobId) {

		var applicant = applicantRepo.findByEmail(username)
				.orElseThrow(() -> new BusinessException("Applicant with username: %s is not found".formatted(username)));
		var job = jobRepo.findById(jobId)
				.orElseThrow(() -> new BusinessException("Job ID: %s is not found".formatted(jobId)));

		var jobApplyPk = new JobApplyPk(applicant.getId(), job.getId());
		var jobApply = new JobApply();

		jobApply.setId(jobApplyPk);
		jobApply.setApplicant(applicant);
		jobApply.setJob(job);
		jobApply.setStatus(ApplicationStatus.APPLIED);

		jobApplyRepo.save(jobApply);

		return new ModificationResult<String>("You successfully applied job");
	}

	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<String> cancelJob(String username, long jobId) {

		var jobApply = jobApplyRepo.findOneByApplicantandJob(username, jobId)
				.orElseThrow(() -> new BusinessException("Job ID: %s is not found".formatted(jobId)));
		jobApplyRepo.delete(jobApply);

		return new ModificationResult<String>("You cancelled the applied job");
	}

	private Function<CriteriaBuilder, CriteriaQuery<JobApplicationListItem>> queryFuncForApplicantList(Long jobId) {
		return cb -> {
			var cq = cb.createQuery(JobApplicationListItem.class);
			var root = cq.from(JobApply.class);

			JobApplicationListItem.select(cq, root);

			var param = new ArrayList<Predicate>();

			if (null != jobId) {
				param.add(cb.equal(root.get(JobApply_.id).get(JobApplyPk_.jobId), jobId));
			}

			cq.where(param.toArray(size -> new Predicate[size]));
			cq.orderBy(cb.asc(root.get(JobApply_.createdAt)));

			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<ApplicantAppliedJobListItem>> queryFuncForJobList(
			Long applicantId) {
		return cb -> {
			var cq = cb.createQuery(ApplicantAppliedJobListItem.class);
			var root = cq.from(JobApply.class);

			ApplicantAppliedJobListItem.select(cq, root);
			var param = new ArrayList<Predicate>();

			if (null != applicantId) {
				param.add(cb.equal(root.get(JobApply_.id).get(JobApplyPk_.applicantId), applicantId));
			}

			cq.where(param.toArray(size -> new Predicate[size]));
			cq.orderBy(cb.asc(root.get(JobApply_.createdAt)));

			return cq;
		};
	}

}
