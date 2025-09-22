package com.project.mausamservice.api.dto.getcitymausam;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dateTime",
    "dateTimeUtc",
    "temperature",
    "visibility",
    "wind",
    "humidity",
    "sunrise",
    "sunriseUtc",
    "sunset",
    "sunsetUtc",
    "weatherStatement",
    "weatherDescription"
})
@Data
public class Weather {

    private LocalDateTime dateTime;
    private LocalDateTime dateTimeUtc;
    private Temperature temperature;
    private Visibility visibility;
    private Wind wind;
    private Humidity humidity;
    private LocalDateTime sunrise;
    private LocalDateTime sunriseUtc;
    private LocalDateTime sunset;
    private LocalDateTime sunsetUtc;
    private String weatherStatement;
    private String weatherDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
