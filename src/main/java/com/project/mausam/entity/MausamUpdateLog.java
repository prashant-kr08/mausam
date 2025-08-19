package com.project.mausam.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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
@Table(name = "t_mausam_update_log")
public class MausamUpdateLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "mausam_id")
	private Mausam oldMausam;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
	@JoinColumn(name = "history_id")
	private MausamHistory mausamHistory;
	private LocalDateTime updateTime;
}
