package com.project.apigateway.enums;

public enum Gender {

	MALE(0, "Male"),
	FEMALE(1, "Female"),
	OTHER(2, "Other");
	
	private int genderCode;
	private String genderName;
	
	private Gender(int genderCode, String genderName) {
		this.genderCode = genderCode;
		this.genderName = genderName;
	}

	public int getGenderCode() {
		return genderCode;
	}

	public String getGenderName() {
		return genderName;
	}
	
	public static Gender getGenderByCode(final int code) {
		for(Gender gender : Gender.values()) {
			if(gender.getGenderCode() == code) {
				return gender;
			}
		}
	    throw new IllegalArgumentException("Invalid gender code: " + code);
	}
	
	public static Gender getGenderByGenderName(final String genderName) {
		for(Gender gender : Gender.values()) {
			if(gender.getGenderName().equalsIgnoreCase(genderName)) {
				return gender;
			}
		}
		throw new IllegalArgumentException("Invalid gender code: " + genderName);
	}

}
