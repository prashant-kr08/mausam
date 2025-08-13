package com.project.mausam.api.dto.savecitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "cityMausam", "savingRemarks"})
@Data
public class SaveCityMausamResponse {
	private Long id;
	private CityMausamResponse cityMausam;
	private String savingRemarks;
}
