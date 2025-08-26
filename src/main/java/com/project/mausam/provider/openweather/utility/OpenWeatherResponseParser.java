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
import com.project.mausam.provider.openweather.dto.CityOpenWeatherResponse;
import com.project.mausam.provider.openweather.dto.Coord;
import com.project.mausam.provider.openweather.dto.Main;
import com.project.mausam.provider.openweather.dto.Sys;
import com.project.mausam.utility.MausamCommonHelper;
import com.project.mausam.utility.WeatherApisUnits;
import com.project.mausam.utility.WeatherApisVisibilityUnit;
import com.project.mausam.utility.WeatherApisWindSpeedUnit;

@Component
@Lazy
public class OpenWeatherResponseParser {
	
	MausamProperties mausamProperties;
	MausamCommonHelper mausamCommonHelper;
	
	public OpenWeatherResponseParser(MausamProperties mausamProperties, MausamCommonHelper mausamCommonHelper) {
		this.mausamProperties = mausamProperties;
		this.mausamCommonHelper = mausamCommonHelper;
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
		location.setTimezone(mausamCommonHelper.getZoneIdFromUnixTimeZoneUtc(openWeatherResponse.getTimezone()));
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
		weather.setDateTime(mausamCommonHelper.getDateTimeFromUnixUtc(openWeatherDt, systemTimeZone));
		weather.setDateTimeUtc(mausamCommonHelper.getDateTimeFromUnixUtc(openWeatherDt, ZoneOffset.UTC));
		weather.setSunrise(mausamCommonHelper.getDateTimeFromUnixUtc(sunrise, systemTimeZone));
		weather.setSunriseUtc(mausamCommonHelper.getDateTimeFromUnixUtc(sunrise, ZoneOffset.UTC));
		weather.setSunset(mausamCommonHelper.getDateTimeFromUnixUtc(sunset, systemTimeZone));
		weather.setSunsetUtc(mausamCommonHelper.getDateTimeFromUnixUtc(sunset, ZoneOffset.UTC));

		List<com.project.mausam.provider.openweather.dto.Weather> openWeatherWeather = openWeatherResponse.getWeather();
		if(!(null == openWeatherResponse) && openWeatherWeather.size() > 0) {
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
