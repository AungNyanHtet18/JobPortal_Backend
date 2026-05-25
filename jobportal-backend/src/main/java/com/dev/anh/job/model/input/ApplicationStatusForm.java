package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.ApplicantionStatus;

import jakarta.validation.constraints.NotNull;

public record ApplicationStatusForm(
	@NotNull(message = "Please enter job id.")
	Long jobId,
	@NotNull(message = "Please enter application id.")
	Long applicantId,
	@NotNull(message = "Please enter application status.")
	ApplicantionStatus status,
	String note) {

}
