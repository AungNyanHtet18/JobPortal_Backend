package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.SkillType;
import com.dev.anh.job.model.entity.Skill;

public record ApplicantSkillDetails(
	SkillType skillType,
	String skillName) {

	public static ApplicantSkillDetails from(Skill skill) {
		 return new ApplicantSkillDetails(skill.getSkillType(), skill.getSkillName());
	}
	
}
