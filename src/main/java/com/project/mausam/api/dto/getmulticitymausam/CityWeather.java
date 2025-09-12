package com.project.mausam.api.dto.getmulticitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"city", "CityMausamResponse"})
@Data
public class CityWeather {
	private String city;
	private CityMausamResponse cityWeather;
}
