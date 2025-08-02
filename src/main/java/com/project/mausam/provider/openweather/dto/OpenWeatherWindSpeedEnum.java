package com.project.mausam.provider.openweather.dto;

public enum OpenWeatherWindSpeedEnum {
	STANDARD(0, "meter/sec"),
	METRIC(1, "meter/sec"),
	IMPERIAL(2, "miles/hour");
	
	int unitsId;
	String windSpeedUnit;
	
	OpenWeatherWindSpeedEnum(final int unitsCode, final String windSpeedUnit) {
		this.unitsId = unitsCode;
		this.windSpeedUnit = windSpeedUnit;
	}

	public int getUnitsId() {
		return unitsId;
	}

	public String getWindSpeedUnit() {
		return windSpeedUnit;
	}

	public static String getWindSpeedUnitfromUnitsId(final int id) {
		for(OpenWeatherWindSpeedEnum windSpeedEnum : values()) {
			if(windSpeedEnum.getUnitsId() == id) {
				return windSpeedEnum.getWindSpeedUnit();
			}
		}
		return STANDARD.getWindSpeedUnit();
	}
}
