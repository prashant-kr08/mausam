package com.project.mausam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mausam.entity.MausamUpdateLog;

@Repository
public interface MausamUpdateLogRepository extends JpaRepository<MausamUpdateLog, Long>{

}
