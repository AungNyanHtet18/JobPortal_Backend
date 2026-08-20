package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChatMessageForm(
	@NotNull(message = "Recipient account is required")
	Long recipientId,
	@NotBlank(message = "Please enter message")
	String content) {

}
