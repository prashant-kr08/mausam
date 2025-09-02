package com.project.mausam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausam.api.dto.getcitymausam.MausamApiResponse;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamRequest;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausam.api.dto.updatecitymausam.UpdateCityMausamResponse;
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
	@PreAuthorize("hasAuthority('FETCH')")
	public ResponseEntity<?>getCityWeather(@Valid @RequestBody final CityMausamRequest cityMausamRequest) {
		final CityMausamResponse cityMausamResponse = mausamService.getCityWeather(cityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, cityMausamResponse, null));
	}
	
	@PostMapping("/city/save")
	@PreAuthorize("hasAuthority('DBOPERATIONS')")
	public ResponseEntity<?>saveCityWeather(@Valid @RequestBody final SaveCityMausamRequest saveCityMausamRequest) {
		final SaveCityMausamResponse saveCityMausamResponse = mausamService.saveCityWeather(saveCityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, saveCityMausamResponse, null));
	}
	
	@GetMapping("/city")
	@PreAuthorize("hasAuthority('DBOPERATIONS')")
	public ResponseEntity<?>getSavedCityWeatherById(@NotBlank @RequestParam final String id) {
		final List<SaveCityMausamResponse> savedCityMausamResponse = mausamService.getSavedCityWeatherById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, savedCityMausamResponse, null));
	}
	
	@PutMapping("/city/update/{id}")
	@PreAuthorize("hasAuthority('DBOPERATIONS')")
	public ResponseEntity<?>updateSavedCityWeatherToLatestById(@PathVariable final Long id) {
		final UpdateCityMausamResponse updateSavedCityWeatherToLatestById = mausamService.updateSavedCityWeatherToLatestById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, updateSavedCityWeatherToLatestById, null));
	}
	
	@DeleteMapping("/city/delete/{id}") 
	@PreAuthorize("hasAuthority('DBOPERATIONS')")
	public ResponseEntity<?>deleteSavedCityWeatherById(@PathVariable final Long id) {
		mausamService.deleteSavedCityWeatherById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, null, null));
	}

}
