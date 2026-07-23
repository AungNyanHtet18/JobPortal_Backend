package com.dev.anh.job.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.PostReact;
import com.dev.anh.job.model.entity.embeddable.PostReactPk;

public interface PostReactRepo extends BaseRepository<PostReact, PostReactPk>{

	@Query("SELECT r FROM PostReact r WHERE r.account.email = :email AND r.id.postId = :postId")
	Optional<PostReact> findOneByAccountandPostReact(@Param("email") String email, @Param("postId") long postId);
	
}
