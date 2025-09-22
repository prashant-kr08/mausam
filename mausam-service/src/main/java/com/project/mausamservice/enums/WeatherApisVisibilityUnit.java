package com.project.mausamservice.enums;

public enum WeatherApisVisibilityUnit {
	
	KELVIN(0, "meter", "kilometer"),
	CELSIUS(1, "meter", "kilometer"),
	FAHRENHEIT(2, "meter", "miles");
	
	int unitId;
	String openWeatherVisibilityUnit;
	String weatherStackVisibilityUnit;
	
	private WeatherApisVisibilityUnit(int unitId, String openWeatherVisibilityUnit, String weatherStackVisibilityUnit) {
		this.unitId = unitId;
		this.openWeatherVisibilityUnit = openWeatherVisibilityUnit;
		this.weatherStackVisibilityUnit = weatherStackVisibilityUnit;
	}
	
	public int getUnitId() {
		return unitId;
	}

	public String getOpenWeatherVisibilityUnit() {
		return openWeatherVisibilityUnit;
	}

	public String getWeatherStackVisibilityUnit() {
		return weatherStackVisibilityUnit;
	}

	public static String getOpenWeatherVisibilityUnitByUnitId(final int unitId) {
		for(WeatherApisVisibilityUnit weatherApisVisibilityUnit : WeatherApisVisibilityUnit.values()) {
			if(weatherApisVisibilityUnit.getUnitId() == unitId) {
				return weatherApisVisibilityUnit.getOpenWeatherVisibilityUnit();
			}
		}
		return KELVIN.getOpenWeatherVisibilityUnit();
	}
	
	public static String getWeatherStackVisibilityUnitByUnitId(final int unitId) {
		for(WeatherApisVisibilityUnit weatherApisVisibilityUnit : WeatherApisVisibilityUnit.values()) {
			if(weatherApisVisibilityUnit.getUnitId() == unitId) {
				return weatherApisVisibilityUnit.getWeatherStackVisibilityUnit();
			}
		}
		return KELVIN.getWeatherStackVisibilityUnit();
	}

}
