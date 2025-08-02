package com.project.mausam.provider.openweather.dto;

public enum OpenWeatherUnits {
	STANDARD(0, "standard", "Kelvin"),
	METRIC(1, "metric", "Celsius"),
	IMPERIAL(2, "imperial", "Fahrenheit");

	private int unitId;
	private String openWeatherUnit;
	private String standardWeatherUnit;
	
	OpenWeatherUnits(int unitId, String openWeatherUnit, String standardWeatherUnit) {
		this.unitId = unitId;
		this.openWeatherUnit = openWeatherUnit;
		this.standardWeatherUnit = standardWeatherUnit;
	}

	public int getId() {
		return this.unitId;
	}
	
	public String getOpenWeatherUnit() {
		return this.openWeatherUnit;
	}
	
	public String getStandardWeatherUnit() {
		return standardWeatherUnit;
	}
	
	public static String getOpenWeatherUnitFromId(final int id) {
		for(OpenWeatherUnits openWeatherUnit : OpenWeatherUnits.values()) {
			if(openWeatherUnit.getId() == id) {
				return openWeatherUnit.getOpenWeatherUnit();
			}
		}
		return STANDARD.getOpenWeatherUnit();
	}
	
	public static String getStandardWeatherUnitFromId(final int id) {
		for(OpenWeatherUnits openWeatherUnit : OpenWeatherUnits.values()) {
			if(openWeatherUnit.getId() == id) {
				return openWeatherUnit.getStandardWeatherUnit();
			}
		}
		return STANDARD.getStandardWeatherUnit();
	}
	
	public static String getStandardWeatherUnitfromOpenWeatherUnit(final String openWeatherUnitName) {
		for(OpenWeatherUnits openWeatherUnit : OpenWeatherUnits.values()) {
			if(openWeatherUnit.getOpenWeatherUnit().equalsIgnoreCase(openWeatherUnitName)) {
				return openWeatherUnit.getStandardWeatherUnit();
			}
		}
		return STANDARD.getStandardWeatherUnit();
	}

}
