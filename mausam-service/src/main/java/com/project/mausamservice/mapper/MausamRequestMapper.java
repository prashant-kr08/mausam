package com.project.mausamservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.project.mausamservice.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausamservice.entity.Mausam;

@Component
@Slf4j
public class MausamRequestMapper {
	
	public MausamRequestMapper() {
		log.info("MausamRequestMapper Bean Init.");
	}

	public CityMausamRequest getCityMausamRequestByMausam(final Mausam savedMausam) {
		final CityMausamRequest cityMausamRequest = new CityMausamRequest();
		cityMausamRequest.setCityName(savedMausam.getLocation().getCityName());
		cityMausamRequest.setUnits(savedMausam.getUnitsCode());
		return cityMausamRequest;
	}

}
