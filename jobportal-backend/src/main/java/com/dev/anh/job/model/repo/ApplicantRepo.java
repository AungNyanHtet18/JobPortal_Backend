package com.dev.anh.job.model.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Applicant;

public interface ApplicantRepo extends BaseRepository<Applicant, Long>{

	@Query("SELECT a FROM Applicant a JOIN a.account c WHERE c.email =:email")
	Optional<Applicant> findByEmail(@Param("email") String email);
	
	@Query("SELECT a.id FROM Applicant a JOIN a.account c WHERE c.email =:email")
	Optional<Long> findIdByAccountEmail(String email);
}