package com.project.mausamservice.api.dto.updatecitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.project.mausamservice.api.dto.savecitymausam.SaveCityMausamResponse;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"presentRecord", "pastRecord"})
@Data
public class UpdateCityMausamResponse {
	private SaveCityMausamResponse presentRecord;
	private SaveCityMausamResponse pastRecord;
}
