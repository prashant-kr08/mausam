package com.project.mausam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mausam.entity.Mausam;

@Repository
public interface MausamRepository extends JpaRepository<Mausam, Long> {
	
	List<Mausam> findTop10ByOrderByWeather_DateTimeDesc();


}
