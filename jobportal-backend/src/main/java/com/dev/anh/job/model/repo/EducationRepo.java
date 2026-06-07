package com.dev.anh.job.model.repo;

import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Education;

public interface EducationRepo extends BaseRepository<Education, Long>{

	Optional<Education> findOneByQualificationName(String qualificationName);
	
}
