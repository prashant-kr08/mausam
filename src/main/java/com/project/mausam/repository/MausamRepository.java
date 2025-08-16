package com.project.mausam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mausam.entity.Mausam;

public interface MausamRepository extends JpaRepository<Mausam, Long> {
	
	List<Mausam> findTop10ByOrderByWeather_DateTimeDesc();


}
