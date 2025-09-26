package com.project.mausamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableCaching
public class MausamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MausamServiceApplication.class, args);
	}

}
