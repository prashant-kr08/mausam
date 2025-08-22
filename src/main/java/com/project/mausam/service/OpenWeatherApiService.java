package com.project.mausam.service;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.configuration.OpenWeatherProperties;
import com.project.mausam.entity.Mausam;
import com.project.mausam.provider.openweather.dto.CityOpenWeatherResponse;
import com.project.mausam.provider.openweather.utility.OpenWeatherConstants;
import com.project.mausam.provider.openweather.utility.OpenWeatherResponseParser;
import com.project.mausam.utility.MausamConstants;
import com.project.mausam.utility.WeatherApisUnits;
import com.project.mausam.utility.errorhandling.InvalidUnitsException;

public class OpenWeatherApiService implements WeatherApiService {
	
	final OpenWeatherProperties openWeatherProperties;
	final OpenWeatherResponseParser openWeatherResponseParser;
	final RestTemplate restTemplate;
	
	public OpenWeatherApiService(final OpenWeatherProperties openWeatherProperties, final OpenWeatherResponseParser openWeatherResponseParser, final RestTemplate restTemplate) {
		this.openWeatherProperties = openWeatherProperties;
		this.openWeatherResponseParser = openWeatherResponseParser;
		this.restTemplate = restTemplate;
		System.out.println("OpenWeatherApi init");
	}

	@Override
	public Mausam getCityWeather(final CityMausamRequest cityMausamRequest) {

		final int units = cityMausamRequest.getUnits();
		final String cityName = cityMausamRequest.getCityName();
		
		final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add(OpenWeatherConstants.OPENWEATHER_PARAM_APPID, openWeatherProperties.getApiKey());
		queryParams.add(OpenWeatherConstants.OPENWEATHER_PARAM_QUERY, cityName);
		
		if (units < 0 || units >= 3) {
			throw new InvalidUnitsException();
		}

		final String openWeatherUnit = WeatherApisUnits.getOpenWeatherUnitFromId(units);
		queryParams.add(OpenWeatherConstants.OPENWEATHER_PARAM_UNITS, openWeatherUnit);
		
		URI uri = UriComponentsBuilder
				.fromUriString(openWeatherProperties.getUrl())
				.queryParams(queryParams)
				.build()
				.toUri();
		
		System.out.println("Calling URI: " + uri.toString());
		
        final CityOpenWeatherResponse response = restTemplate.getForObject(uri, CityOpenWeatherResponse.class);
		
		final Mausam mausam = openWeatherResponseParser.parseCityOpenWeatherResponse(response, units);
		mausam.setDataProvider(MausamConstants.PROVIDER_OPEN_WEATHER);
		mausam.setUnitsCode(units);
		mausam.setProviderUnits(openWeatherUnit);
		mausam.setUpdatedAt(LocalDateTime.now());
		return mausam;
	}

}
