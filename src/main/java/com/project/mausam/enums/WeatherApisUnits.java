package com.project.mausam.enums;

public enum WeatherApisUnits {
	KELVIN(0, "standard", "s", "Kelvin"), //openWeather default
	CELCIUS(1, "metric", "m", "Celsius"), //weatherSatck deafult
	FAHRENHEIT(2, "imperial","f", "Fahrenheit");

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
		return KELVIN.getOpenWeatherUnit();
	}
	
	public static String getWeatherStackUnitFromId(final int id) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getId() == id) {
				return weatherApisUnit.getWeatherStackUnit();
			}
		}
		return KELVIN.getWeatherStackUnit();
	}
	
	public static String getStandardWeatherUnitFromId(final int id) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getId() == id) {
				return weatherApisUnit.getStandardWeatherUnit();
			}
		}
		return KELVIN.getStandardWeatherUnit();
	}
	
	public static String getStandardWeatherUnitfromOpenWeatherUnit(final String openWeatherUnitName) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getOpenWeatherUnit().equalsIgnoreCase(openWeatherUnitName)) {
				return weatherApisUnit.getStandardWeatherUnit();
			}
		}
		return KELVIN.getStandardWeatherUnit();
	}
	
	public static String getStandardWeatherUnitfromWeatherStackUnit(final String weatherStackUnitName) {
		for(WeatherApisUnits weatherApisUnit : WeatherApisUnits.values()) {
			if(weatherApisUnit.getWeatherStackUnit().equalsIgnoreCase(weatherStackUnitName)) {
				return weatherApisUnit.getStandardWeatherUnit();
			}
		}
		return KELVIN.getStandardWeatherUnit();
	}

}
