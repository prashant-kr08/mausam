package com.project.mausam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_mausam",
uniqueConstraints = @UniqueConstraint(columnNames = {"cityName", "unitsCode", "dataProvider", "dateTime",  "timezone"} ))
public class Mausam {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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
	private String savingRemarks;
	@Transient
	private String traceId;

}
