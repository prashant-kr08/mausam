package com.project.mausam.mapper;

import org.springframework.stereotype.Component;

import com.project.mausam.api.dto.auth.SignUpResponse;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausam.api.dto.getcitymausam.Humidity;
import com.project.mausam.api.dto.getcitymausam.Location;
import com.project.mausam.api.dto.getcitymausam.Temperature;
import com.project.mausam.api.dto.getcitymausam.Trace;
import com.project.mausam.api.dto.getcitymausam.Visibility;
import com.project.mausam.api.dto.getcitymausam.Weather;
import com.project.mausam.api.dto.getcitymausam.WeatherData;
import com.project.mausam.api.dto.getcitymausam.Wind;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.User;
import com.project.mausam.utility.MausamConstants;

@Component
public class MausamResponseMapper {
	
	public MausamResponseMapper() {
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
		location.setTimezone(mausamLocation.getTimezone());
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

	public SignUpResponse getSignUpResponseByUser(final User savedUser) {
		final SignUpResponse signUpResponse = new SignUpResponse();
		signUpResponse.setName(String.join(" ", savedUser.getFirstName(), savedUser.getLastName()).trim());
		signUpResponse.setUserName(savedUser.getUsername());
		signUpResponse.setRegisterAt(savedUser.getCreatedAt());
		return SignUpResponse;
	}
	
}
