package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LanguageLevel {
	Beginner("Beginner"),
	Elementary("Elementary"),
	PreIntermediate("Pre Intermediate"),
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
	
	@JsonCreator //handle incoming JSON conversion safely
	public static LanguageLevel fromString(String value) {
		for(LanguageLevel type: LanguageLevel.values()){ 
			if(type.readableName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
				 return type;
			}
		}
		
		throw new IllegalArgumentException("Unknown Language Level:"+ value);
	}
	
	
}
