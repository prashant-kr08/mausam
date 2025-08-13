package com.project.mausam.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class MausamProperties {

	private String timezone;

	public MausamProperties() {
		System.out.println("MausamProperties init");
	}
}
