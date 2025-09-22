package com.project.mausamservice.api.dto.getmulticitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.mausamservice.api.dto.getcitymausam.CityMausamResponse;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"city", "CityMausamResponse"})
@Data
public class CityWeather {
	private String city;
	private CityMausamResponse cityWeather;
}
