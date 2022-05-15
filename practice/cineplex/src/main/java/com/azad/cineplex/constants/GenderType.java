package com.azad.cineplex.constants;

public enum GenderType {

	MALE("male"),
	FEMALE("female"),
	OTHER("other");
	
	private String value;
	
	private GenderType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
