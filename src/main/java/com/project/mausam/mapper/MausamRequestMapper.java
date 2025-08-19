package com.project.mausam.mapper;

import org.springframework.stereotype.Component;

import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.entity.Mausam;

@Component
public class MausamRequestMapper {

	public CityMausamRequest getCityMausamRequestByMausam(final Mausam savedMausam) {
		final CityMausamRequest cityMausamRequest = new CityMausamRequest();
		cityMausamRequest.setCityName(savedMausam.getLocation().getCityName());
		cityMausamRequest.setUnits(savedMausam.getUnitsCode());
		return cityMausamRequest;
	}

}
