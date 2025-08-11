package com.project.mausam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Temperature {
	@Column(name = "current_temp")
    private Double current;
    private Double feelsLike;
    @Column(name = "min_temp")
    private Double min;
    @Column(name = "max_temp")
    private Double max;
    @Column(name = "temp_unit")
    private String unit;
}
