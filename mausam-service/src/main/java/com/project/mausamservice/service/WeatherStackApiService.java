package com.project.mausamservice.service;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.mausamservice.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausamservice.configuration.WeatherStackProperties;
import com.project.mausamservice.entity.Mausam;
import com.project.mausamservice.enums.WeatherApisUnits;
import com.project.mausamservice.provider.weatherstack.dto.CityWeatherStackResponse;
import com.project.mausamservice.provider.weatherstack.utility.WeatherStackConstants;
import com.project.mausamservice.provider.weatherstack.utility.WeatherStackResponseParser;
import com.project.mausamservice.utility.MausamConstants;
import com.project.mausamservice.utility.errorhandling.InvalidUnitsException;

public class WeatherStackApiService implements WeatherApiService{
	
	final WeatherStackProperties weatherStackProperties;
	final WeatherStackResponseParser weatherStackResponseParser;
	final RestTemplate restTemplate;
	
	
	public WeatherStackApiService(WeatherStackProperties weatherStackProperties, WeatherStackResponseParser weatherStackResponseParser, RestTemplate restTemplate) {
		this.weatherStackProperties = weatherStackProperties;
		this.weatherStackResponseParser = weatherStackResponseParser;
		this.restTemplate = restTemplate;
		System.out.println("Weather stack init");
	}

	@Override
	public Mausam getCityWeather(final CityMausamRequest cityMausamRequest) {
		final String cityName = cityMausamRequest.getCityName();
		final int units = cityMausamRequest.getUnits();
		
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add(WeatherStackConstants.WEATHERSTACK_PARAM_ACCESS_KEY, weatherStackProperties.getApiKey());
		queryParams.add(WeatherStackConstants.WEATHERSTACK_PARAM_QUERY, cityName);
		
		if(units < 0 || units>=3) {
			throw new InvalidUnitsException();
		}
		
		final String weatherStackUnit = WeatherApisUnits.getWeatherStackUnitFromId(units);
		queryParams.add(WeatherStackConstants.WEATHERSTACK_PARAM_UNITS, weatherStackUnit);
		
		URI uri = UriComponentsBuilder
				.fromUriString(weatherStackProperties.getUrl())
				.queryParams(queryParams)
				.build()
				.toUri();
		
		System.out.println("Calling URI: " + uri.toString());
		
		final CityWeatherStackResponse cityWeatherStackResponse = restTemplate.getForObject(uri, CityWeatherStackResponse.class);
		
		final Mausam mausam = weatherStackResponseParser.getMausamFromCityWeatherStackResponse(cityWeatherStackResponse, units);
		mausam.setDataProvider(MausamConstants.PROVIDER_WEATHER_STACK);
		mausam.setUnitsCode(units);
		mausam.setProviderUnits(weatherStackUnit);
		mausam.setUpdatedAt(LocalDateTime.now());
		return mausam;
	}

}
