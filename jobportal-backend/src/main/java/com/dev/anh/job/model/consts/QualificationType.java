package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QualificationType {
	DOCTORATE("Doctorate"),
	PHD("PHD"),
	MASTER("Master"),
	DEGREE("Degree"),
    DIPLOMA("Diploma"),
    POSTGRADUATE_DIPLOMA("Postgraduate Diploma"),
    HIGH_SCHOOL("High School"),
    FOUNDATION_PROGRAM("Foundation Program"),
    COURSE("Course"),
    CERTIFICATE("Certificate"),
    BOOTCAMP("Bootcamp"),
    PROFESSIONAL_TRAINING("Professional Traning"),
    VOCATIONAL("Vocational"),
    TECHNICAL_CERTIFICATION("Technical Certification");
    
	private final String readableName;
	
	private QualificationType(String readableName) {
		 this.readableName = readableName;
	}
	
	@JsonValue
	public String getReadableName() {
		return readableName;
	}
	
	@JsonCreator //handle incoming JSON conversion safely
	public static QualificationType fromString(String value) {
		for(QualificationType type: QualificationType.values()){ 
			if(type.readableName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
				 return type;
			}
		}
		throw new IllegalArgumentException("Unknown Qualification Type:"+ value);
	}
	
	
}
