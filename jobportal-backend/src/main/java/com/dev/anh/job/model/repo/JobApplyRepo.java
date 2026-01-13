package com.dev.anh.job.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk;

public interface JobApplyRepo extends BaseRepository<JobApply, JobApplyPk>{
	
	@Query("SELECT j FROM JobApply j WHERE j.applicant.account.email = :email AND j.id.jobId = :jobId")
	Optional<JobApply> findOneByApplicantandJob(@Param("email")String email, @Param("jobId") long jobId);
	
}
