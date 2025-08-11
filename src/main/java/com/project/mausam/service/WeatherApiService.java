package com.project.mausam.service;

import com.project.mausam.api.dto.CityMausamRequest;
import com.project.mausam.entity.Mausam;

public interface WeatherApiService {
	
	Mausam getCityWeather(final CityMausamRequest cityMausamRequest);

}
