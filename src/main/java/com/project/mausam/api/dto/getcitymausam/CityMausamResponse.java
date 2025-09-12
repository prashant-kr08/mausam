package com.project.mausam.api.dto.getcitymausam;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "weatherData",
    "trace"
})

@Data
public class CityMausamResponse {

    private WeatherData weatherData;
    private Trace trace;
}
