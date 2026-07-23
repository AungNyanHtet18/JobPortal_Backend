package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotBlank;

public record PostCommentForm(
	@NotBlank(message = "Please fill the comment for post")
	String comment){
   
	
}
