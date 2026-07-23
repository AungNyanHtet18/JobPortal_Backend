package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JobLevel {
	Intern("Intern Level"),
	Entry("Entry Level"),
	Junior("Junior Level"),
	Mid("Mid Level"),
	Senior("Senior Level"),
	Lead("Lead Level");
	
	private final String readableName;
	
	private JobLevel(String readableName) {
		 this.readableName = readableName;
	}
	
	@JsonValue
	public String getReadableName() {
		 return readableName;
	}
	
	@JsonCreator //handle incoming JSON conversion safely
	public static JobLevel fromString(String value) {
		for(JobLevel type: JobLevel.values()){ 
			if(type.readableName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
				 return type;
			}
		}
		throw new IllegalArgumentException("Unknown Job Level:"+ value);
	}
}
