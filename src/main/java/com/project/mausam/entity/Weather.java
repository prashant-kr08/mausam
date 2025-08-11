package com.project.mausam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Weather {
    private LocalDateTime dateTime;
    @Embedded
    private Temperature temperature;
    @Embedded
    private Visibility visibility;
    @Embedded
    private Wind wind;
    @Embedded
    private Humidity humidity;
    
    private LocalDateTime sunrise;
    private LocalDateTime sunset;
    private String weatherStatement;
    private String weatherDescription;
}
