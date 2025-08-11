package com.project.mausam.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.project.mausam.api.dto.CityMausamResponse;
import com.project.mausam.api.dto.Humidity;
import com.project.mausam.api.dto.Location;
import com.project.mausam.api.dto.Temperature;
import com.project.mausam.api.dto.Trace;
import com.project.mausam.api.dto.Visibility;
import com.project.mausam.api.dto.Weather;
import com.project.mausam.api.dto.WeatherData;
import com.project.mausam.api.dto.Wind;
import com.project.mausam.entity.Mausam;
import com.project.mausam.utility.MausamConstants;
import com.project.mausam.utility.RedisCacheUtil;

@Component
public class MausamResponseMapper {
	
	RedisCacheUtil redisCacheUtil;
	
	public MausamResponseMapper(RedisCacheUtil redisCacheUtil) {
		this.redisCacheUtil = redisCacheUtil;
		System.out.println("MausamResponseMapper init.");
	}

	public CityMausamResponse getCityMausamResponse(Mausam cityMausam) {
		
		final CityMausamResponse mausamResponse = new CityMausamResponse();
		final WeatherData weatherData = new WeatherData();
		final Location location = new Location();
		
		final com.project.mausam.entity.Location mausamLocation  = cityMausam.getLocation();
		location.setCityName(mausamLocation.getCityName());
		location.setLongitude(mausamLocation.getLongitude());
		location.setLatitude(mausamLocation.getLatitude());
		location.setCountry(mausamLocation.getCountry());
		weatherData.setLocation(location);
		
		final Weather weather = new Weather();
		final Temperature temperature = new Temperature();
		final com.project.mausam.entity.Weather mausamWeather = cityMausam.getWeather();
		final com.project.mausam.entity.Temperature mausamTemperature = mausamWeather.getTemperature();
		temperature.setCurrent(mausamTemperature.getCurrent());
		temperature.setFeelsLike(mausamTemperature.getFeelsLike());
		temperature.setMin(mausamTemperature.getMin());
		temperature.setMax(mausamTemperature.getMax());
		temperature.setUnit(mausamTemperature.getUnit());
		weather.setTemperature(temperature);

		final Visibility visibility = new Visibility();
		final com.project.mausam.entity.Visibility mausamVisibility = mausamWeather.getVisibility();
		visibility.setValue(mausamVisibility.getValue());
		visibility.setUnit(mausamVisibility.getUnit());
		weather.setVisibility(visibility);
	
		final Wind wind = new Wind();
		final com.project.mausam.entity.Wind mausamWind = mausamWeather.getWind();
		wind.setSpeed(mausamWind.getSpeed());
		wind.setWindSpeedUnit(mausamWind.getWindSpeedUnit());
		weather.setWind(wind);
		
		final Humidity humidity = new Humidity();
		final com.project.mausam.entity.Humidity mausamHumidity = mausamWeather.getHumidity();
		humidity.setValue(mausamHumidity.getValue());
		humidity.setUnit(mausamHumidity.getUnit());
		weather.setHumidity(humidity);
		
		weather.setWeatherStatement(mausamWeather.getWeatherStatement());
		weather.setWeatherDescription(mausamWeather.getWeatherStatement());
		
		weatherData.setWeather(weather);
		mausamResponse.setWeatherData(weatherData);
		
		final String traceId = UUID.randomUUID().toString();
		
		final Trace trace = new Trace();
		trace.setId(traceId);
		trace.setExpiryInMinutes(MausamConstants.MAUSAM_CACHE_EXPIRY_TIME_IN_MINUTES);
		mausamResponse.setTrace(trace);
		
		redisCacheUtil.addToCacheWithTtlInMinutes(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, traceId,
				cityMausam, trace.getExpiryInMinutes());
		
		return mausamResponse;
	}
	
}
