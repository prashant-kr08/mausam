package com.project.mausamservice.controller;

import com.project.mausamservice.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausamservice.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausamservice.api.dto.getmulticitymausam.MultiCityMausamRequest;
import com.project.mausamservice.api.dto.getmulticitymausam.MultiCityMausamResponse;
import com.project.mausamservice.api.dto.mausam.MausamApiResponse;
import com.project.mausamservice.api.dto.savecitymausam.SaveCityMausamRequest;
import com.project.mausamservice.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausamservice.api.dto.updatecitymausam.UpdateCityMausamResponse;
import com.project.mausamservice.service.MausamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mausam")
public class MausamController {
	
	MausamService mausamService;
	
	public MausamController(MausamService mausamService) {
		this.mausamService = mausamService;
	}
	
	@PostMapping("/city")
	public ResponseEntity<?>getCityWeather(@Valid @RequestBody final CityMausamRequest cityMausamRequest) {
		final CityMausamResponse cityMausamResponse = mausamService.getCityWeather(cityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, cityMausamResponse, null));
	}
	
	@PostMapping("/multi-city")
	public ResponseEntity<?>getMultiCityWeather(@Valid @RequestBody final MultiCityMausamRequest multiCityMausamRequest) {
		final MultiCityMausamResponse multiCityMausamResponse = mausamService.getMultiCityWeather(multiCityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, multiCityMausamResponse, null));
	}
	
	@PostMapping("/city/save")
	public ResponseEntity<?>saveCityWeather(@Valid @RequestBody final SaveCityMausamRequest saveCityMausamRequest) {
		final SaveCityMausamResponse saveCityMausamResponse = mausamService.saveCityWeather(saveCityMausamRequest);
		return ResponseEntity.ok(new MausamApiResponse<>(true, saveCityMausamResponse, null));
	}
	
	@GetMapping("/saved/city")
	public ResponseEntity<?>getSavedCityWeatherById(@NotBlank @RequestParam final String id) {
		final List<SaveCityMausamResponse> savedCityMausamResponse = mausamService.getSavedCityWeatherById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, savedCityMausamResponse, null));
	}
	
	@PutMapping("/city/update/{id}")
	public ResponseEntity<?>updateSavedCityWeatherToLatestById(@PathVariable final Long id) {
		final UpdateCityMausamResponse updateSavedCityWeatherToLatestById = mausamService.updateSavedCityWeatherToLatestById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, updateSavedCityWeatherToLatestById, null));
	}
	
	@DeleteMapping("/city/delete/{id}") 
	public ResponseEntity<?>deleteSavedCityWeatherById(@PathVariable final Long id) {
		mausamService.deleteSavedCityWeatherById(id);
		return ResponseEntity.ok(new MausamApiResponse<>(true, null, null));
	}

}
