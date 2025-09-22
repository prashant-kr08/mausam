package com.project.mausamservice.provider.weatherstack.dto;

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
    "co",
    "no2",
    "o3",
    "so2",
    "pm2_5",
    "pm10",
    "us-epa-index",
    "gb-defra-index"
})
@Data
public class AirQuality {

    private String co;
    private String no2;
    private String o3;
    private String so2;
    @JsonProperty("pm2_5")
    private String pm25;
    private String pm10;
    @JsonProperty("us-epa-index")
    private String usEpaIndex;
    @JsonProperty("gb-defra-index")
    private String gbDefraIndex;
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
