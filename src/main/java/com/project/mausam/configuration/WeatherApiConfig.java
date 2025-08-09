package com.project.mausam.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.project.mausam.service.OpenWeatherApiService;
import com.project.mausam.service.WeatherApiService;
import com.project.mausam.service.WeatherStackApiService;
import com.project.mausam.utility.RedisCacheUtil;

@Configuration
public class WeatherApiConfig {
	
	@Bean
	@ConditionalOnProperty(name = "openweather.enabled", havingValue = "true", matchIfMissing = true)
	@Primary
	public WeatherApiService openWeatherApiService(OpenWeatherProperties openWeatherProperties, RedisCacheUtil redisCacheUtil) {
		return new OpenWeatherApiService(openWeatherProperties, redisCacheUtil);
	}
	
	@Bean
	@ConditionalOnProperty(name = "weatherstack.enabled", havingValue = "true", matchIfMissing = false)
	public WeatherApiService weatherStackApiService() {
		return new WeatherStackApiService();
	}

}
