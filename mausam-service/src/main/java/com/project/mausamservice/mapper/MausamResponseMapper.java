package com.project.mausamservice.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.project.mausamservice.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausamservice.api.dto.getcitymausam.Humidity;
import com.project.mausamservice.api.dto.getcitymausam.Location;
import com.project.mausamservice.api.dto.getcitymausam.Temperature;
import com.project.mausamservice.api.dto.getcitymausam.Trace;
import com.project.mausamservice.api.dto.getcitymausam.Visibility;
import com.project.mausamservice.api.dto.getcitymausam.Weather;
import com.project.mausamservice.api.dto.getcitymausam.WeatherData;
import com.project.mausamservice.api.dto.getcitymausam.Wind;
import com.project.mausamservice.api.dto.getmulticitymausam.CityWeather;
import com.project.mausamservice.api.dto.getmulticitymausam.MultiCityMausamResponse;
import com.project.mausamservice.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausamservice.entity.Mausam;
import com.project.mausamservice.utility.MausamConstants;

@Component
public class MausamResponseMapper {
	
	public MausamResponseMapper() {
		System.out.println("MausamResponseMapper init.");
	}

	public CityMausamResponse getCityMausamResponse(Mausam cityMausam) {
		
		final CityMausamResponse mausamResponse = new CityMausamResponse();
		final WeatherData weatherData = new WeatherData();
		final Location location = new Location();
		
		final com.project.mausamservice.entity.Location mausamLocation  = cityMausam.getLocation();
		location.setCityName(mausamLocation.getCityName());
		location.setLongitude(mausamLocation.getLongitude());
		location.setLatitude(mausamLocation.getLatitude());
		location.setCountry(mausamLocation.getCountry());
		location.setTimezone(mausamLocation.getTimezone());
		weatherData.setLocation(location);
		
		final Weather weather = new Weather();
		final Temperature temperature = new Temperature();
		final com.project.mausamservice.entity.Weather mausamWeather = cityMausam.getWeather();
		final com.project.mausamservice.entity.Temperature mausamTemperature = mausamWeather.getTemperature();
		temperature.setCurrent(mausamTemperature.getCurrent());
		temperature.setFeelsLike(mausamTemperature.getFeelsLike());
		temperature.setMin(mausamTemperature.getMin());
		temperature.setMax(mausamTemperature.getMax());
		temperature.setUnit(mausamTemperature.getUnit());
		weather.setTemperature(temperature);

		final Visibility visibility = new Visibility();
		final com.project.mausamservice.entity.Visibility mausamVisibility = mausamWeather.getVisibility();
		visibility.setValue(mausamVisibility.getValue());
		visibility.setUnit(mausamVisibility.getUnit());
		weather.setVisibility(visibility);
	
		final Wind wind = new Wind();
		final com.project.mausamservice.entity.Wind mausamWind = mausamWeather.getWind();
		wind.setSpeed(mausamWind.getSpeed());
		wind.setWindSpeedUnit(mausamWind.getWindSpeedUnit());
		weather.setWind(wind);
		
		final Humidity humidity = new Humidity();
		final com.project.mausamservice.entity.Humidity mausamHumidity = mausamWeather.getHumidity();
		humidity.setValue(mausamHumidity.getValue());
		humidity.setUnit(mausamHumidity.getUnit());
		weather.setHumidity(humidity);
		
		weather.setWeatherStatement(mausamWeather.getWeatherStatement());
		weather.setWeatherDescription(mausamWeather.getWeatherDescription());
		
		weather.setDateTime(mausamWeather.getDateTime());
		weather.setDateTimeUtc(mausamWeather.getDateTimeUtc());
		weather.setSunrise(mausamWeather.getSunrise());
		weather.setSunriseUtc(mausamWeather.getSunriseUtc());
		weather.setSunset(mausamWeather.getSunset());
		weather.setSunsetUtc(mausamWeather.getSunsetUtc());

		weatherData.setWeather(weather);
		mausamResponse.setWeatherData(weatherData);
		
		if(null != cityMausam.getTraceId()) {
			final Trace trace = new Trace();
			trace.setId(cityMausam.getTraceId());
			trace.setExpiryInMinutes(MausamConstants.MAUSAM_CACHE_EXPIRY_TIME_IN_MINUTES);
			mausamResponse.setTrace(trace);
		}
		
		return mausamResponse;
	}

	public SaveCityMausamResponse getSaveCityMausamResponse(final Mausam savedMausam) {
		final CityMausamResponse cityMausamResponse = getCityMausamResponse(savedMausam);
		final SaveCityMausamResponse saveCityMausamResponse = new SaveCityMausamResponse();
		saveCityMausamResponse.setId(savedMausam.getId());
		saveCityMausamResponse.setWeatherData(cityMausamResponse.getWeatherData());
		saveCityMausamResponse.setSavingRemarks(savedMausam.getSavingRemarks());
		return saveCityMausamResponse;
	}

	public MultiCityMausamResponse getMultiCityMausamResponse(final List<String> cities, final Map<String, CityMausamResponse> cityWiseResponse) {
		final MultiCityMausamResponse multiCityMausamResponse = new MultiCityMausamResponse();
		List<CityWeather> weathers = new ArrayList<>();
		for(String city : cities) {
			CityWeather cityWeather = new CityWeather();
			cityWeather.setCity(city);
			cityWeather.setCityWeather(cityWiseResponse.get(city));
			weathers.add(cityWeather);
		}
		multiCityMausamResponse.setCitiesWeather(weathers);
		return multiCityMausamResponse;
	}
	
}
