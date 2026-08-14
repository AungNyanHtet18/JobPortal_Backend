package com.dev.anh.job.model.repo;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.AccountFollow;
import com.dev.anh.job.model.entity.embeddable.AccountFollowPk;

public interface AccountFollowRepo extends BaseRepository<AccountFollow, AccountFollowPk>{
	
	boolean existsByIdFollowerIdAndFollowingId(Long followerId, Long followingId);
	void deleteByIdFollowerIdAndFollowingId(Long followerId, Long followingId);
	long countByFollowerId(Long accountId);
	long countByFollowingId(Long accountId);
}
