package com.project.mausamservice.entity;

import java.time.ZoneId;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Location {
	private String cityName;
	private String country;
	private Double longitude;
	private Double latitude;
	private ZoneId timezone;
}
