package com.project.mausam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Wind {
	@Column(name = "wind_speed")
	private Double speed;
	private String windSpeedUnit;
}
