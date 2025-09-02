package com.project.mausam.api.dto.savecitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "traceId", "savingRemarks" })
@Data
public class SaveCityMausamRequest {
	@NotBlank(message = "trace id is required.")
	private String traceId;
	private String savingRemarks;
}
