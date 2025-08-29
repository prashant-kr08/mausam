package com.project.mausam.provider.weatherstack.utility;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.project.mausam.configuration.MausamProperties;
import com.project.mausam.entity.Humidity;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.Temperature;
import com.project.mausam.entity.Visibility;
import com.project.mausam.entity.Weather;
import com.project.mausam.entity.Wind;
import com.project.mausam.provider.weatherstack.dto.Astro;
import com.project.mausam.provider.weatherstack.dto.CityWeatherStackResponse;
import com.project.mausam.provider.weatherstack.dto.Current;
import com.project.mausam.provider.weatherstack.dto.Location;
import com.project.mausam.utility.DateTimeUtil;
import com.project.mausam.utility.WeatherApisUnits;
import com.project.mausam.utility.WeatherApisVisibilityUnit;
import com.project.mausam.utility.WeatherApisWindSpeedUnit;

@Component
public class WeatherStackResponseParser {
	
	private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER_12H = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
	
	private final MausamProperties mausamProperties;
	private final DateTimeUtil dateTimeUtil;
	
	public WeatherStackResponseParser(MausamProperties mausamProperties, DateTimeUtil dateTimeUtil) {
		this.mausamProperties = mausamProperties;
		this.dateTimeUtil = dateTimeUtil;
	}

	public Mausam getMausamFromCityWeatherStackResponse(final CityWeatherStackResponse cityWeatherStackResponse, final int units) {

		final Mausam mausam = new Mausam();
		final Location weatherStackLocation = cityWeatherStackResponse.getLocation();
		com.project.mausam.entity.Location mausamLocation = new com.project.mausam.entity.Location();
		mausamLocation.setCityName(weatherStackLocation.getName());
		mausamLocation.setCountry(weatherStackLocation.getCountry());
		mausamLocation.setLongitude(weatherStackLocation.getLon());
		mausamLocation.setLatitude(weatherStackLocation.getLat());

		final Double utcOffset = weatherStackLocation.getUtcOffset();
		ZoneOffset zoneOffset = null;
		if(utcOffset != null) {
			zoneOffset = dateTimeUtil.getZoneOffset(utcOffset);
			mausamLocation.setTimezone(ZoneId.ofOffset("UTC", zoneOffset));
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
		
		final String systemTimeZone = mausamProperties.getTimezone();
		mausamWeather.setSystemTimeZone(systemTimeZone);
		
		final String weatherStackLocalDatetime = weatherStackLocation.getLocaltime();
		final String[] dateTimeSplit = weatherStackLocalDatetime.split(" ");
		final Astro astro = current.getAstro();
		final String weatherStackSunriseTime = astro.getSunrise();
		final String weatherStackSunsetTime = astro.getSunset();
		final String sunrise = String.join(" ", dateTimeSplit[0], weatherStackSunriseTime); 
		final String sunset = String.join(" ", dateTimeSplit[0], weatherStackSunsetTime); 
		
		mausamWeather.setDateTime(dateTimeUtil.getZonedDateTime(weatherStackLocalDatetime, LOCAL_DATE_TIME_FORMATTER, systemTimeZone));
		mausamWeather.setDateTimeUtc(dateTimeUtil.toUTC(weatherStackLocalDatetime, zoneOffset, LOCAL_DATE_TIME_FORMATTER));
		mausamWeather.setSunrise(dateTimeUtil.getZonedDateTime(sunrise, LOCAL_DATE_TIME_FORMATTER_12H, systemTimeZone));
		mausamWeather.setSunriseUtc(dateTimeUtil.toUTC(sunrise, zoneOffset, LOCAL_DATE_TIME_FORMATTER_12H));
		mausamWeather.setSunset(dateTimeUtil.getZonedDateTime(sunset, LOCAL_DATE_TIME_FORMATTER_12H, systemTimeZone));
		mausamWeather.setSunsetUtc(dateTimeUtil.toUTC(sunset, zoneOffset, LOCAL_DATE_TIME_FORMATTER_12H));
		
		final String weatherDescription = Optional.ofNullable(current.getWeatherDescriptions())
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.orElse(null);
		
		mausamWeather.setWeatherDescription(weatherDescription);
		mausamWeather.setWeatherStatement(weatherDescription);
		
		final String traceId = UUID.randomUUID().toString();
		mausam.setTraceId(traceId);
		mausam.setWeather(mausamWeather);
		return mausam;
	}

}
