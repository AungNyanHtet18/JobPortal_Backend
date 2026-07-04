package com.dev.anh.job.event;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.dev.anh.job.model.repo.PostRepo;
import com.dev.anh.job.utils.FileProvider;
import com.dev.anh.job.utils.exception.BusinessException;
import com.dev.anh.job.utils.exception.FileInvalidException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostPhotoEventListener {

	private final PostRepo postRepo;
	private final FileProvider fileProvider;
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PostPhotoEvent event) {
        
		fileProvider.validateFile(event.getFile(), Set.of("png", "jpg", "jpeg")); 
		
		try {
	        var fileName = fileProvider.saveFile(
			            uploadPath.concat("/post"),
			            event.getUsername(),
			            event.getFile());
	
	    	var post = postRepo.findById(event.getPostId())
				       .orElseThrow(() -> new BusinessException("Post with %d is not found".formatted(event.getPostId())));
	
	         post.setPostPhoto(fileName);
	         postRepo.save(post);
		 }catch (IOException e) {
			throw new FileInvalidException("Profile upload failed", e);
		}
    }
	
}
