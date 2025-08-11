package com.project.mausam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Visibility {
    
	@Column(name = "visibility")
    private Long value;
	@Column(name = "visibility_unit")
    private String unit;
}
