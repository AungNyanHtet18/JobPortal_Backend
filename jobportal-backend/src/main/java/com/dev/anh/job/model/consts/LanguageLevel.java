package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LanguageLevel {
	Beginner("Beginner"),
	Elementary("Elementary"),
	PreIntermediate("PreIntermediate"),
	Intermediate("Intermediate"),
	UpperIntermediate("Upper Intermediate"),
	Advanced("Advanced"),
	Native("Native");
	
	private final String readableName;
	
	private LanguageLevel(String readableName) {
		this.readableName = readableName;
	}
	
	// Tells Jackson to use this value in JSON responses
	@JsonValue
	public String getReadableName() {
		 return readableName;
	}
	
	
}
