package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonValue;

public enum JobLevel {
	Intern("Intern Level"),
	Entry("Entry Level"),
	Mid("Mid Level"),
	Junior("Junior Level"),
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
}
