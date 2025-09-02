package com.project.mausam.enums;

public enum WeatherApisWindSpeedUnit {
	KELVIN(0, "meter/sec", "kilometer/hour"),
	CELSIUS(1, "meter/sec", "kilometer/hour"),
	FAHRENHEIT(2, "miles/hour", "miles/hour");
	
	int unitsId;
	String openWeatherWindSpeedUnit;
	String weatherStackWindSpeedUnit;

	WeatherApisWindSpeedUnit(final int unitsCode, final String openWeatherWindSpeedUnit, final String weatherStackWindSpeedUnit) {
		this.unitsId = unitsCode;
		this.openWeatherWindSpeedUnit = openWeatherWindSpeedUnit;
		this.weatherStackWindSpeedUnit = weatherStackWindSpeedUnit;
	}

	public int getUnitsId() {
		return unitsId;
	}

	public String getOpenWeatherWindSpeedUnit() {
		return openWeatherWindSpeedUnit;
	}

	public String getWeatherStackWindSpeedUnit() {
		return weatherStackWindSpeedUnit;
	}
	
	public static String getOpenWeatherWindSpeedUnitfromUnitsId(final int id) {
		for(WeatherApisWindSpeedUnit weatherApisWindSpeedEnum : values()) {
			if(weatherApisWindSpeedEnum.getUnitsId() == id) {
				return weatherApisWindSpeedEnum.getOpenWeatherWindSpeedUnit();
			}
		}
		return KELVIN.getOpenWeatherWindSpeedUnit();
	}
	public static String getWeatherStackWindSpeedUnitfromUnitsId(final int id) {
		for(WeatherApisWindSpeedUnit weatherApisWindSpeedEnum : values()) {
			if(weatherApisWindSpeedEnum.getUnitsId() == id) {
				return weatherApisWindSpeedEnum.getWeatherStackWindSpeedUnit();
			}
		}
		return KELVIN.getOpenWeatherWindSpeedUnit();
	}
}
