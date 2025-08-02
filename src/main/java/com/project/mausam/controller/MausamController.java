package com.project.mausam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mausam.api.dto.CityMausamRequest;
import com.project.mausam.api.dto.CityMausamResponse;
import com.project.mausam.api.dto.MausamApiResponse;
import com.project.mausam.service.MausamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mausam")
public class MausamController {
	
	MausamService mausamService;
	
	public MausamController(MausamService mausamService) {
		this.mausamService = mausamService;
	}
	
	@PostMapping("/city")
	public ResponseEntity<?>getCityWeather(@Valid @RequestBody CityMausamRequest cityMausamRequest) {
		final CityMausamResponse cityMausamResponse = mausamService.getCityWeather(cityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, cityMausamResponse, null));
	}

}
