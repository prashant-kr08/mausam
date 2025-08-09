package com.project.mausam.service;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.mausam.api.dto.CityMausamRequest;
import com.project.mausam.api.dto.CityMausamResponse;
import com.project.mausam.api.dto.Humidity;
import com.project.mausam.api.dto.Location;
import com.project.mausam.api.dto.Temperature;
import com.project.mausam.api.dto.Trace;
import com.project.mausam.api.dto.Visibility;
import com.project.mausam.api.dto.Weather;
import com.project.mausam.api.dto.WeatherData;
import com.project.mausam.api.dto.Wind;
import com.project.mausam.configuration.OpenWeatherProperties;
import com.project.mausam.provider.openweather.dto.CityOpenWeatherResponse;
import com.project.mausam.provider.openweather.dto.Coord;
import com.project.mausam.provider.openweather.dto.Main;
import com.project.mausam.provider.openweather.dto.OpenWeatherUnits;
import com.project.mausam.provider.openweather.dto.OpenWeatherWindSpeedEnum;
import com.project.mausam.provider.openweather.dto.Sys;
import com.project.mausam.provider.openweather.utility.OpenWeatherConstants;
import com.project.mausam.utility.MausamConstants;
import com.project.mausam.utility.RedisCacheUtil;
import com.project.mausam.utility.errorhandling.InvalidUnitsException;


public class OpenWeatherApiService implements WeatherApiService {
	
	final OpenWeatherProperties openWeatherProperties;
	final RedisCacheUtil redisCacheUtil;
	
	public OpenWeatherApiService(final OpenWeatherProperties openWeatherProperties, final RedisCacheUtil redisCacheUtil) {
		this.openWeatherProperties = openWeatherProperties;
		this.redisCacheUtil = redisCacheUtil;
		System.out.println("OpenWeatherApi init");
	}

	@Override
	public CityMausamResponse getCityWeather(final CityMausamRequest cityMausamRequest) {

		final int units = cityMausamRequest.getUnits();
		final String cityName = cityMausamRequest.getCityName();
		
		RestTemplate restTemplate = new RestTemplate();
		
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add(OpenWeatherConstants.OPENWEATHER_PARAM_APPID, openWeatherProperties.getApiKey());
		queryParams.add(OpenWeatherConstants.OPENWEATHER_PARAM_QUERY, cityName);
		
		if (units < 0 || units >= 3) {
			throw new InvalidUnitsException();
		}

		if (units != 0) {
			String openWeatherUnit = OpenWeatherUnits.getOpenWeatherUnitFromId(units);
			queryParams.add(OpenWeatherConstants.OPENWEATHER_PARAM_UNITS, openWeatherUnit);
		}
		
		URI uri = UriComponentsBuilder
				.fromUriString(openWeatherProperties.getUrl())
				.queryParams(queryParams)
				.build()
				.toUri();
		
		System.out.println("Calling URI: " + uri.toString());

		
        CityOpenWeatherResponse response = restTemplate.getForObject(uri, CityOpenWeatherResponse.class);
		
		return parseCityOpenWeatherResponse(response, units);
	}

	private CityMausamResponse parseCityOpenWeatherResponse(final CityOpenWeatherResponse openWeatherResponse, final int units) {
		
		final CityMausamResponse mausamResponse = new CityMausamResponse();
		final WeatherData weatherData = new WeatherData();
		final Location location = new Location();
		final Coord coord = openWeatherResponse.getCoord();
		final Sys sys = openWeatherResponse.getSys();
		
		location.setCityName(openWeatherResponse.getName());
		location.setLongitude(coord.getLon());
		location.setLatitude(coord.getLat());
		location.setCountry(sys.getCountry());
		weatherData.setLocation(location);
		
		final Weather weather = new Weather();
		final Temperature temperature = new Temperature();
		final Main main = openWeatherResponse.getMain();
		
		temperature.setCurrent(main.getTemp());
		temperature.setFeelsLike(main.getFeelsLike());
		temperature.setMin(main.getTempMin());
		temperature.setMax(main.getTempMax());
		temperature.setUnit(OpenWeatherUnits.getStandardWeatherUnitFromId(units));
		weather.setTemperature(temperature);

		final Visibility visibility = new Visibility();
		visibility.setValue(openWeatherResponse.getVisibility());
		visibility.setUnit(OpenWeatherConstants.OPENWEATHER_VISIBILITY_UNIT);
		weather.setVisibility(visibility);
	
		final Wind wind = new Wind();
		final com.project.mausam.provider.openweather.dto.Wind openWeatherWind = openWeatherResponse.getWind();
		wind.setSpeed(openWeatherWind.getSpeed());
		wind.setWindSpeedUnit(OpenWeatherWindSpeedEnum.getWindSpeedUnitfromUnitsId(units));
		weather.setWind(wind);
		
		final Humidity humidity = new Humidity();
		humidity.setValue(main.getHumidity());
		humidity.setUnit(OpenWeatherConstants.OPENWEATHER_HUMIDITY_UNIT);
		weather.setHumidity(humidity);
		
		List<com.project.mausam.provider.openweather.dto.Weather> openWeatherWeather = openWeatherResponse.getWeather();
		if(!(null == openWeatherResponse) && openWeatherWeather.size() > 0) {
			com.project.mausam.provider.openweather.dto.Weather details = openWeatherWeather.get(0);
			weather.setWeatherStatement(details.getMain());
			weather.setWeatherDescription(details.getDescription());
		}
		
		weatherData.setWeather(weather);
		mausamResponse.setWeatherData(weatherData);
		
		final String traceId = UUID.randomUUID().toString();
		
		final Trace trace = new Trace();		trace.setId(traceId);
		trace.setExpiryInMinutes(MausamConstants.MAUSAM_CACHE_EXPIRY_TIME_IN_MINUTES);
		mausamResponse.setTrace(trace);
		
		redisCacheUtil.addToCacheWithTtlInMinutes(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, traceId,
				mausamResponse, trace.getExpiryInMinutes());
		
		return mausamResponse;
	}



}
