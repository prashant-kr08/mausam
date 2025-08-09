package com.project.mausam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MausamApplication {

	public static void main(String[] args) {
		SpringApplication.run(MausamApplication.class, args);
	}

}
