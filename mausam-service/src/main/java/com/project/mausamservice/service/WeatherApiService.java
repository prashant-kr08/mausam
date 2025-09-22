package com.project.mausamservice.service;

import com.project.mausamservice.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausamservice.entity.Mausam;

public interface WeatherApiService {
	
	Mausam getCityWeather(final CityMausamRequest cityMausamRequest);

}
