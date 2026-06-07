package com.dev.anh.job.model.repo;

import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Skill;

public interface SkillRepo extends BaseRepository<Skill, Long>{

	Optional<Skill> findOneBySkillName(String skillName);
	
	
}
