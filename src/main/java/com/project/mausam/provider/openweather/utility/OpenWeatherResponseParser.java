package com.project.mausam.provider.openweather.utility;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.project.mausam.configuration.MausamProperties;
import com.project.mausam.entity.Humidity;
import com.project.mausam.entity.Location;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.Temperature;
import com.project.mausam.entity.Visibility;
import com.project.mausam.entity.Weather;
import com.project.mausam.entity.Wind;
import com.project.mausam.enums.WeatherApisUnits;
import com.project.mausam.enums.WeatherApisVisibilityUnit;
import com.project.mausam.enums.WeatherApisWindSpeedUnit;
import com.project.mausam.provider.openweather.dto.CityOpenWeatherResponse;
import com.project.mausam.provider.openweather.dto.Coord;
import com.project.mausam.provider.openweather.dto.Main;
import com.project.mausam.provider.openweather.dto.Sys;
import com.project.mausam.utility.DateTimeUtil;

@Component
@Lazy
public class OpenWeatherResponseParser {
	
	MausamProperties mausamProperties;
	DateTimeUtil dateTimeUtil;
	
	public OpenWeatherResponseParser(MausamProperties mausamProperties, DateTimeUtil dateTimeUtil) {
		this.mausamProperties = mausamProperties;
		this.dateTimeUtil = dateTimeUtil;
		System.out.println("OpenWeatherResponseParser init.");
	}

	public Mausam parseCityOpenWeatherResponse(final CityOpenWeatherResponse openWeatherResponse, final int units) {

		final Mausam mausam = new Mausam();
		final Location location = new Location();
		final Coord coord = openWeatherResponse.getCoord();
		final Sys sys = openWeatherResponse.getSys();

		location.setCityName(openWeatherResponse.getName());
		location.setLongitude(coord.getLon());
		location.setLatitude(coord.getLat());
		location.setCountry(sys.getCountry());
		location.setTimezone(dateTimeUtil.getZoneIdFromUnixTimeZoneUtc(openWeatherResponse.getTimezone()));
		mausam.setLocation(location);

		final Weather weather = new Weather();
		final Temperature temperature = new Temperature();
		final Main main = openWeatherResponse.getMain();

		temperature.setCurrent(main.getTemp());
		temperature.setFeelsLike(main.getFeelsLike());
		temperature.setMin(main.getTempMin());
		temperature.setMax(main.getTempMax());
		temperature.setUnit(WeatherApisUnits.getStandardWeatherUnitFromId(units));
		weather.setTemperature(temperature);

		final Visibility visibility = new Visibility();
		visibility.setValue(openWeatherResponse.getVisibility());
		visibility.setUnit(WeatherApisVisibilityUnit.getOpenWeatherVisibilityUnitByUnitId(units));
		weather.setVisibility(visibility);

		final Wind wind = new Wind();
		final com.project.mausam.provider.openweather.dto.Wind openWeatherWind = openWeatherResponse.getWind();
		wind.setSpeed(openWeatherWind.getSpeed());
		wind.setWindSpeedUnit(WeatherApisWindSpeedUnit.getOpenWeatherWindSpeedUnitfromUnitsId(units));
		weather.setWind(wind);

		final Humidity humidity = new Humidity();
		humidity.setValue(main.getHumidity());
		humidity.setUnit(OpenWeatherConstants.OPENWEATHER_HUMIDITY_UNIT);
		weather.setHumidity(humidity);
		
		weather.setSystemTimeZone(mausamProperties.getTimezone());
		final ZoneId systemTimeZone = ZoneId.of(mausamProperties.getTimezone());
		
		final Long openWeatherDt = openWeatherResponse.getDt();
		final Long sunrise = sys.getSunrise();
		final Long sunset = sys.getSunset();
		weather.setDateTime(dateTimeUtil.getDateTimeFromUnixUtc(openWeatherDt, systemTimeZone));
		weather.setDateTimeUtc(dateTimeUtil.getDateTimeFromUnixUtc(openWeatherDt, ZoneOffset.UTC));
		weather.setSunrise(dateTimeUtil.getDateTimeFromUnixUtc(sunrise, systemTimeZone));
		weather.setSunriseUtc(dateTimeUtil.getDateTimeFromUnixUtc(sunrise, ZoneOffset.UTC));
		weather.setSunset(dateTimeUtil.getDateTimeFromUnixUtc(sunset, systemTimeZone));
		weather.setSunsetUtc(dateTimeUtil.getDateTimeFromUnixUtc(sunset, ZoneOffset.UTC));

		List<com.project.mausam.provider.openweather.dto.Weather> openWeatherWeather = openWeatherResponse.getWeather();
		if(openWeatherResponse!=null && !openWeatherWeather.isEmpty()) {
			com.project.mausam.provider.openweather.dto.Weather details = openWeatherWeather.get(0);
			weather.setWeatherStatement(details.getMain());
			weather.setWeatherDescription(details.getDescription());
		}
		
		final String traceId = UUID.randomUUID().toString();
		mausam.setTraceId(traceId);
		mausam.setWeather(weather);

		return mausam;
	}

}
