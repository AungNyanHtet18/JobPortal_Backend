package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotNull;

public record UnReadMessageSenderRequestList(
	@NotNull(message = "Please fill sender id.")
	Long senderId) {

}
