package com.project.mausam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "openweather")
@Data
public class OpenWeatherProperties {
	private String url;
	private String apiKey;
	
	OpenWeatherProperties() {
		System.out.println("openWeatherProperties init");
	}
}
