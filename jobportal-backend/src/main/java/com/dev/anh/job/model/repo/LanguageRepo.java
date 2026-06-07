package com.dev.anh.job.model.repo;

import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Language;

public interface LanguageRepo extends BaseRepository<Language, Long>{

	Optional<Language> findOneByName(String name);
	
}
