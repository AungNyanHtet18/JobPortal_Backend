package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SkillType {
	SoftSkill("Soft Skill"), 
	HardSkill("Hard Skill");
	
	private final String readableName;
	
	private SkillType(String readableName) {
		 this.readableName = readableName;
	}
	
	@JsonValue
	public String getReadableName() {
		 return readableName;
	}	
}
