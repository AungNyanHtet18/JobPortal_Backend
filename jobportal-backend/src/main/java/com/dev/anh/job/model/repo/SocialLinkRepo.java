package com.dev.anh.job.model.repo;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.SocialLink;

public interface SocialLinkRepo extends BaseRepository<SocialLink, Long>{
	void deleteByApplicant(Applicant applicant);

}
