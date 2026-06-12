package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
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
	
	@JsonCreator //handle incoming JSON conversion safely
	public static SkillType fromString(String value) {
		for(SkillType type: SkillType.values()){ 
			if(type.readableName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
				 return type;
			}
		}
		throw new IllegalArgumentException("Unknown Skill Type:"+ value);
	}
	
	
	
	
}
