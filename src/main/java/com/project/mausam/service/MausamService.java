package com.project.mausam.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.project.mausam.api.dto.CityMausamRequest;
import com.project.mausam.api.dto.CityMausamResponse;
import com.project.mausam.utility.MausamConstants;

@Service
public class MausamService {
	
	WeatherApiService weatherApiService;
	
	public MausamService(WeatherApiService weatherApiService) {
		this.weatherApiService = weatherApiService;
		System.out.println("MausamService init");
	}
	
	@Cacheable(value = MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, key = "#cityMausamRequest.cityName + '_' + #cityMausamRequest.units")
	public CityMausamResponse getCityWeather(final CityMausamRequest cityMausamRequest) {
		return weatherApiService.getCityWeather(cityMausamRequest);
	}

	
}
