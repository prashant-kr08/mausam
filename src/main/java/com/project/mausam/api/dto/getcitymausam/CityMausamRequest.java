package com.project.mausam.api.dto.getcitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cityName", "units" })
@Data
public class CityMausamRequest {

	@NotBlank(message = "City name is required")
	private String cityName;
	private int units;

}