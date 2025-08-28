package com.project.mausam.provider.weatherstack.dto;

import java.util.LinkedHashMap;
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
    "name",
    "country",
    "region",
    "lat",
    "lon",
    "timezone_id",
    "localtime",
    "localtime_epoch",
    "utc_offset"
})
@Data
public class Location {

    private String name;
    private String country;
    private String region;
    private Double lat;
    private Double lon;
    @JsonProperty("timezone_id")
    private String timezoneId;
    private String localtime;
    @JsonProperty("localtime_epoch")
    private Long localtimeEpoch;
    @JsonProperty("utc_offset")
    private Double utcOffset;
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
