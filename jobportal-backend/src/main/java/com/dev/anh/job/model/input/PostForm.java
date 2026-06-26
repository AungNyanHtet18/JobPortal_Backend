package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotBlank;

public record PostForm(
	@NotBlank(message = "Please enter content for post.")
    String content) {

}
