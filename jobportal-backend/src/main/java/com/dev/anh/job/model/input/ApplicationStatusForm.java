package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.ApplicationStatus;

import jakarta.validation.constraints.NotNull;

public record ApplicationStatusForm(
	@NotNull(message = "Please enter application id.")
	Long applicantId,
	@NotNull(message = "Please enter application status.")
	ApplicationStatus status,
	String note) {
}
