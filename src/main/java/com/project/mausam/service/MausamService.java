package com.project.mausam.service;

import org.springframework.stereotype.Service;

import com.project.mausam.api.dto.CityMausamRequest;
import com.project.mausam.api.dto.CityMausamResponse;

@Service
public class MausamService {
	
	WeatherApiService weatherApiService;
	
	public MausamService(WeatherApiService weatherApiService) {
		this.weatherApiService = weatherApiService;
		System.out.println("MausamService init");
	}
	
	public CityMausamResponse getCityWeather(final CityMausamRequest cityMausamRequest) {
		return weatherApiService.getCityWeather(cityMausamRequest);
	}

	
}
