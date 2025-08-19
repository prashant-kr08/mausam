package com.project.mausam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "t_mausam_history")
public class MausamHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne(optional = false)
	@JoinColumn(name = "mausam_id", nullable = false)
	private Mausam mausamHistory;
	private String dataProvider;
	private Integer unitsCode;
	private String providerUnits;
	@Embedded
	private Location location;
	@Embedded
	private Weather weather;
	private Boolean updatedLater;
	private LocalDateTime updatedAt;
	private String savingRemarks;
	@OneToOne(mappedBy = "savedMausamHistory", cascade = CascadeType.ALL, orphanRemoval = true)
	private MausamUpdateLog updateLog;
}
