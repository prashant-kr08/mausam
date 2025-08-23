package com.project.mausam.utility;

public enum WeatherApisUnits {
	STANDARD(0, "standard", "s", "Kelvin"), //openWeather default
	METRIC(1, "metric", "m", "Celsius"), //weatherSatck deafult
	IMPERIAL(2, "imperial","f", "Fahrenheit");

	private int unitId;
	private String openWeatherUnit;
	private String weatherSatckUnit;
	private String standardWeatherUnit;
	
	WeatherApisUnits(int unitId, String openWeatherUnit, String weatherStackUnit, String standardWeatherUnit) {
		this.unitId = unitId;
		this.openWeatherUnit = openWeatherUnit;
		this.weatherSatckUnit = weatherStackUnit;
		this.standardWeatherUnit = standardWeatherUnit;
	}

	public int getId() {
		return this.unitId;
	}
	
	public String getOpenWeatherUnit() {
		return this.openWeatherUnit;
	}
	
	public String getWeatherStackUnit() {
		return this.weatherSatckUnit;
	}
	
	public String getStandardWeatherUnit() {
		return standardWeatherUnit;
	}
	
	public static String getOpenWeatherUnitFromId(final int id) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getId() == id) {
				return weatherApisUnit.getOpenWeatherUnit();
			}
		}
		return STANDARD.getOpenWeatherUnit();
	}
	
	public static String getWeatherStackUnitFromId(final int id) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getId() == id) {
				return weatherApisUnit.getWeatherStackUnit();
			}
		}
		return STANDARD.getWeatherStackUnit();
	}
	
	public static String getStandardWeatherUnitFromId(final int id) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getId() == id) {
				return weatherApisUnit.getStandardWeatherUnit();
			}
		}
		return STANDARD.getStandardWeatherUnit();
	}
	
	public static String getStandardWeatherUnitfromOpenWeatherUnit(final String openWeatherUnitName) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getOpenWeatherUnit().equalsIgnoreCase(openWeatherUnitName)) {
				return weatherApisUnit.getStandardWeatherUnit();
			}
		}
		return STANDARD.getStandardWeatherUnit();
	}
	
	public static String getStandardWeatherUnitfromWeatherStackUnit(final String weatherStackUnitName) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getWeatherStackUnit().equalsIgnoreCase(weatherStackUnitName)) {
				return weatherApisUnit.getStandardWeatherUnit();
			}
		}
		return STANDARD.getStandardWeatherUnit();
	}

}
