package com.dev.anh.job.model.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.SavedJob;
import com.dev.anh.job.model.entity.embeddable.SavedJobPk;

public interface SavedJobRepo extends BaseRepository<SavedJob,SavedJobPk>{

	@Query("SELECT s FROM SavedJob s WHERE s.applicant.account.email = :email AND s.id.jobId = :jobId")
	Optional<SavedJob> findOneByApplicantandJob(@Param("email") String email, @Param("jobId") long jobId);

	@Query("SELECT s.id.jobId FROM SavedJob s  WHERE s.id.applicantId = :applicantId AND s.savedJob = :savedJob")
	List<Long> findJobIdsByApplicantandSavedJob(@Param("applicantId") Long applicantId, @Param("savedJob") boolean savedJob);

}
