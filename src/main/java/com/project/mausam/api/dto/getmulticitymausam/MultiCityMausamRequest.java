package com.project.mausam.api.dto.getmulticitymausam;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"cities", "units"})
@Data
public class MultiCityMausamRequest {
	
	@NotEmpty(message = "City names is required")
	private List<String> cities;
	private int units;

}
