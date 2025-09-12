package com.project.mausam.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;

@Service
public class MausamAsyncService {
	
	private final MausamService mausamService;

	public MausamAsyncService(MausamService mausamService) {
		this.mausamService = mausamService;
	}

	@Async("fastExecutor")
	public CompletableFuture<CityMausamResponse> getCityWeatherAsync(CityMausamRequest cityMausamRequest) {
		System.out.println(Thread.currentThread().getName());
		return CompletableFuture.completedFuture(mausamService.getCityWeather(cityMausamRequest));
	}

}
