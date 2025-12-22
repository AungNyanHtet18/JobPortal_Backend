package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshForm {

	@NotBlank(message =  "Please enter token to refresh.")
	private String token;
}
