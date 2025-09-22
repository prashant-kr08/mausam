package com.project.mausamservice.provider.weatherstack.dto;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "observation_time",
    "temperature",
    "weather_code",
    "weather_icons",
    "weather_descriptions",
    "astro",
    "air_quality",
    "wind_speed",
    "wind_degree",
    "wind_dir",
    "pressure",
    "precip",
    "humidity",
    "cloudcover",
    "feelslike",
    "uv_index",
    "visibility",
    "is_day"
})
@Data
public class Current {

    @JsonProperty("observation_time")
    private String observationTime;
    private Double temperature;
    @JsonProperty("weather_code")
    private Long weatherCode;
    @JsonProperty("weather_icons")
    private List<String> weatherIcons;
    @JsonProperty("weather_descriptions")
    private List<String> weatherDescriptions;
    private Astro astro;
    @JsonProperty("air_quality")
    private AirQuality airQuality;
    @JsonProperty("wind_speed")
    private Double windSpeed;
    @JsonProperty("wind_degree")
    private Long windDegree;
    @JsonProperty("wind_dir")
    private String windDir;
    private Long pressure;
    private Long precip;
    private Long humidity;
    private Long cloudcover;
    private Double feelslike;
    @JsonProperty("uv_index")
    private Long uvIndex;
    private Long visibility;
    @JsonProperty("is_day")
    private String isDay;
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
