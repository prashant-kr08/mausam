package com.project.mausam.service;

import com.project.mausam.api.dto.CityMausamRequest;
import com.project.mausam.api.dto.CityMausamResponse;

public interface WeatherApiService {
	
	CityMausamResponse getCityWeather(final CityMausamRequest cityMausamRequest);

}
