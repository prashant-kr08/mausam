package com.project.mausam.provider.weatherstack.utility;

import java.time.ZoneId;
import java.time.ZoneOffset;

import com.project.mausam.configuration.MausamProperties;
import com.project.mausam.entity.Humidity;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.Temperature;
import com.project.mausam.entity.Visibility;
import com.project.mausam.entity.Weather;
import com.project.mausam.entity.Wind;
import com.project.mausam.provider.weatherstack.dto.CityWeatherStackResponse;
import com.project.mausam.provider.weatherstack.dto.Current;
import com.project.mausam.provider.weatherstack.dto.Location;
import com.project.mausam.utility.WeatherApisUnits;
import com.project.mausam.utility.WeatherApisVisibilityUnit;
import com.project.mausam.utility.WeatherApisWindSpeedUnit;

public class WeatherStackResponseParser {
	
	MausamProperties mausamProperties;
	
	public WeatherStackResponseParser(MausamProperties mausamProperties) {
		this.mausamProperties = mausamProperties;
	}

	public Mausam getMausamFromCityWeatherStackResponse(final CityWeatherStackResponse cityWeatherStackResponse, final int units) {

		final Mausam mausam = new Mausam();
		final Location weatherStackLocation = cityWeatherStackResponse.getLocation();
		com.project.mausam.entity.Location mausamLocation = new com.project.mausam.entity.Location();
		mausamLocation.setCityName(weatherStackLocation.getName());
		mausamLocation.setCountry(weatherStackLocation.getCountry());
		mausamLocation.setLongitude(weatherStackLocation.getLon());
		mausamLocation.setLatitude(weatherStackLocation.getLat());

		final String utcOffset = weatherStackLocation.getUtcOffset();
		if(utcOffset != null && !utcOffset.isBlank()) {
			mausamLocation.setTimezone(ZoneId.ofOffset("UTC", ZoneOffset.of(utcOffset)));
		}
		mausam.setLocation(mausamLocation);
		
		final Current current = cityWeatherStackResponse.getCurrent();
		final Weather mausamWeather = new Weather();
		final Temperature temperature = new Temperature();
		temperature.setCurrent(current.getTemperature());
		temperature.setFeelsLike(current.getFeelslike());
		temperature.setUnit(WeatherApisUnits.getStandardWeatherUnitFromId(units));
		mausamWeather.setTemperature(temperature);
		
		final Wind wind = new Wind();
		wind.setSpeed(current.getWindSpeed());
		wind.setWindSpeedUnit(WeatherApisWindSpeedUnit.getWeatherStackWindSpeedUnitfromUnitsId(units));
		mausamWeather.setWind(wind);
		
		final Humidity humidity = new Humidity();
		humidity.setValue(current.getHumidity());
		humidity.setUnit(WeatherStackConstants.WEATHERSTACK_HUMIDITY_UNIT);
		mausamWeather.setHumidity(humidity);
		
		final Visibility visibility = new Visibility();
		visibility.setValue(current.getVisibility());
		visibility.setUnit(WeatherApisVisibilityUnit.getWeatherStackVisibilityUnitByUnitId(units));
		mausamWeather.setVisibility(visibility);
		
		mausamWeather.setSystemTimeZone(mausamProperties.getTimezone());
		
		mausam.setWeather(mausamWeather);
		return mausam;
	}

}
