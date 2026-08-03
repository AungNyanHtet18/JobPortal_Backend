package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionType {
	SingleChoice("Single Choice"),
	MultipleChoice("Multiple Choice");
	
	private final String readableName;
	
	private QuestionType(String readableName) {
		 this.readableName = readableName;
	}
	
	@JsonValue
	public String getReadableName() {
		 return readableName;
	}
	
	@JsonCreator //handle incoming JSON conversion safely
	public static QuestionType fromString(String value) {
		for(QuestionType type: QuestionType.values()){ 
			if(type.readableName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
				 return type;
			}
		}
		throw new IllegalArgumentException("Unknown Question Type:"+ value);
	}
}
