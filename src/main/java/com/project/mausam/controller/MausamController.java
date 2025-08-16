package com.project.mausam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausam.api.dto.getcitymausam.MausamApiResponse;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamRequest;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausam.service.MausamService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

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
	
	@PostMapping("/city/save")
	public ResponseEntity<?>saveCityWeather(@Valid @RequestBody SaveCityMausamRequest saveCityMausamRequest) {
		final SaveCityMausamResponse saveCityMausamResponse = mausamService.saveCityWeather(saveCityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, saveCityMausamResponse, null));
	}
	
	@GetMapping("/city")
	public ResponseEntity<?>getSavedCityWeatherById(@NotBlank @RequestParam String id) {
		final List<SaveCityMausamResponse> savedCityMausamResponse = mausamService.getSavedCityWeatherById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, savedCityMausamResponse, null));
	}
	

}
