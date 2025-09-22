package com.project.mausamservice.provider.openweather.utility;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.project.mausamservice.configuration.MausamProperties;
import com.project.mausamservice.entity.Humidity;
import com.project.mausamservice.entity.Location;
import com.project.mausamservice.entity.Mausam;
import com.project.mausamservice.entity.Temperature;
import com.project.mausamservice.entity.Visibility;
import com.project.mausamservice.entity.Weather;
import com.project.mausamservice.entity.Wind;
import com.project.mausamservice.enums.WeatherApisUnits;
import com.project.mausamservice.enums.WeatherApisVisibilityUnit;
import com.project.mausamservice.enums.WeatherApisWindSpeedUnit;
import com.project.mausamservice.provider.openweather.dto.CityOpenWeatherResponse;
import com.project.mausamservice.provider.openweather.dto.Coord;
import com.project.mausamservice.provider.openweather.dto.Main;
import com.project.mausamservice.provider.openweather.dto.Sys;
import com.project.mausamservice.utility.DateTimeUtil;

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
		final com.project.mausamservice.provider.openweather.dto.Wind openWeatherWind = openWeatherResponse.getWind();
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

		List<com.project.mausamservice.provider.openweather.dto.Weather> openWeatherWeather = openWeatherResponse.getWeather();
		if(openWeatherResponse!=null && !openWeatherWeather.isEmpty()) {
			com.project.mausamservice.provider.openweather.dto.Weather details = openWeatherWeather.get(0);
			weather.setWeatherStatement(details.getMain());
			weather.setWeatherDescription(details.getDescription());
		}
		
		final String traceId = UUID.randomUUID().toString();
		mausam.setTraceId(traceId);
		mausam.setWeather(weather);

		return mausam;
	}

}
