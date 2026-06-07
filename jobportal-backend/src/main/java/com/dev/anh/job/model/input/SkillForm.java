package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.SkillType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SkillForm(
	@NotNull(message = "Please choose skill type.")
	SkillType skillType,
	@NotBlank(message = "Please fill your skill name.")
	String skillName) {

}
