package com.dev.anh.job.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.PostComment;
import com.dev.anh.job.model.output.CommentListItem;

public interface PostCommentRepo extends BaseRepository<PostComment, Long> {
	
	@Query("""
	        SELECT new com.dev.anh.job.model.output.CommentListItem(
	            c.comment, 
	            a.name, 
	            CASE WHEN a.role = com.dev.anh.job.model.consts.Role.Applicant 
	                 THEN app.profilePhoto 
	                 ELSE comp.profilePhoto 
	            END
	        )
	        FROM PostComment c
	        JOIN c.account a
	        LEFT JOIN a.applicant app
	        LEFT JOIN a.company comp
	        WHERE c.post.id = :postId
	    """)
	List<CommentListItem> findCommentListByPostId(@Param("postId") Long postId);

}