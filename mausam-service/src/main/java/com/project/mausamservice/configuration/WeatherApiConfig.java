package com.project.mausamservice.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.project.mausamservice.provider.openweather.utility.OpenWeatherResponseParser;
import com.project.mausamservice.provider.weatherstack.utility.WeatherStackResponseParser;
import com.project.mausamservice.service.OpenWeatherApiService;
import com.project.mausamservice.service.WeatherApiService;
import com.project.mausamservice.service.WeatherStackApiService;

@Configuration
public class WeatherApiConfig {
	
	@Bean
	@ConditionalOnProperty(name = "openweather.enabled", havingValue = "true", matchIfMissing = true)
	@Primary
	public WeatherApiService openWeatherApiService(OpenWeatherProperties openWeatherProperties, OpenWeatherResponseParser openWeatherResponseParser, RestTemplate restTemplate) {
		return new OpenWeatherApiService(openWeatherProperties, openWeatherResponseParser, restTemplate);
	}
	
	@Bean
	@ConditionalOnProperty(name = "weatherstack.enabled", havingValue = "true", matchIfMissing = false)
	public WeatherApiService weatherStackApiService(WeatherStackProperties weatherStackProperties, WeatherStackResponseParser weatherStackResponseParser, RestTemplate restTemplate) {
		return new WeatherStackApiService(weatherStackProperties, weatherStackResponseParser, restTemplate);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
