package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotBlank;

public record CareerRoleForm(
		@NotBlank(message = "Please fill your interested role.")
		String roleName) {

}
