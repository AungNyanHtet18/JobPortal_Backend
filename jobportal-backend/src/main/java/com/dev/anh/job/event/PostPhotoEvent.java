package com.dev.anh.job.event;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostPhotoEvent {
	private Long postId;
	private String username; 
	private MultipartFile file;
}
