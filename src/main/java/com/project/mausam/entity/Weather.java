package com.project.mausam.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Weather {
    private LocalDateTime dateTime;
    private LocalDateTime dateTimeUtc;
    @Embedded
    private Temperature temperature;
    @Embedded
    private Visibility visibility;
    @Embedded
    private Wind wind;
    @Embedded
    private Humidity humidity;
    
    private ZoneId systemTimeZone;
    private LocalDateTime sunrise;
    private LocalDateTime sunriseUtc;
    private LocalDateTime sunset;
    private LocalDateTime sunsetUtc;
    private String weatherStatement;
    private String weatherDescription;
}
