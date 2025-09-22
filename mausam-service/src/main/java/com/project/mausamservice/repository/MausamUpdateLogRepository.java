package com.project.mausamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mausamservice.entity.MausamUpdateLog;

@Repository
public interface MausamUpdateLogRepository extends JpaRepository<MausamUpdateLog, Long>{

}
