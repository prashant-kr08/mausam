package com.project.mausam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "weatherstack")
public class WeatherStackProperties {
	
	private String url;
	private String apiKey;
	
	public WeatherStackProperties() {
		System.out.println("weatherstack properties init.");
	}

}
