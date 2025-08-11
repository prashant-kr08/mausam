package com.project.mausam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_mausam")
public class Mausam {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String dataProvider;
	private int unitsCode;
	private String providerUnits;
	@Embedded
	private Location location;
	@Embedded
	private Weather weather;
	private boolean updatedLater;
	private LocalDateTime updatedAt;

}
