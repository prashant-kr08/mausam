package com.project.mausam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Humidity {
	@Column(name = "humidity")
    private Long value;
	@Column(name = "humidity_unit")
    private String unit;
}
