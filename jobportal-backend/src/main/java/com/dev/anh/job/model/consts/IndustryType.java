package com.dev.anh.job.model.consts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IndustryType {
	
    // Corporate, Finance 
    STAFFING_RECRUITMENT_AGENCY("Staffing & Recruitment Agency"),
    FINANCE_BANKING("Finance, Banking & Fintech"),
    INSURANCE("Insurance Services"),
    HUMAN_RESOURCES("Human Resources & Recruiting"),
    MARKETING_ADVERTISING("Marketing, Advertising & PR"),
    MANAGEMENT_CONSULTING("Management Consulting"),
    LEGAL_SERVICES("Legal Services"),
	
    // Tech, Media & Telecom
    INFORMATION_TECHNOLOGY("Information Technology"),
    SOFTWARE_DEVELOPMENT("Software Development & AI"),
    TELECOMMUNICATIONS("Telecommunications"),
    CYBERSECURITY("Cybersecurity & Network Security"),
    ENTERTAINMENT_MEDIA("Entertainment, Media & Gaming"),
    
    // Healthcare, Science & Pharmacy
    HEALTHCARE_MEDICAL("Healthcare, Hospitals & Medical"),
    PHARMACEUTICALS("Pharmaceuticals & Biotech"),
    MEDICAL_DEVICES("Medical Devices & Equipment"),

    // Commerce, Goods & Hospitality
    RETAIL_ECOMMERCE("Retail & E-Commerce"),
    WHOLESALE_DISTRIBUTION("Wholesale & Distribution"),
    FOOD_BEVERAGE("Food & Beverage Services"),
    HOSPITALITY_TOURISM("Hospitality, Travel & Tourism"),

    // Infrastructure, Industry & Logistics
    CONSTRUCTION_REAL_ESTATE("Construction & Real Estate"),
    LOGISTICS_SUPPLY_CHAIN("Logistics, Supply Chain & Freight"),
    MANUFACTURING_PRODUCTION("Manufacturing & Production"),
    AUTOMOTIVE("Automotive & Aerospace"),
    ENERGY_UTILITIES("Energy, Oil, Gas & Utilities"),

    // Education, Public & Non-Profit
    EDUCATION_ELEARNING("Education & E-Learning"),
    NON_PROFIT_NGO("Non-Profit & NGO"),
    GOVERNMENT_PUBLIC_SECTOR("Government & Public Sector"),
    AGRICULTURE("Agriculture & Farming");

    private final String readableName;
    
	private IndustryType(String readableName) {
		 this.readableName = readableName;
	}
	
	@JsonValue
	public String getReadableName() {
		return readableName;
	}
	
	@JsonCreator //handle incoming JSON conversion safely
	public static IndustryType fromString(String value) {
		for(IndustryType type: IndustryType.values()){ 
			if(type.readableName.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
				 return type;
			}
		}
		
		throw new IllegalArgumentException("Unknown Industry Type:"+ value);
	}
    
}
