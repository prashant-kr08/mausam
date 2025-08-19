package com.project.mausam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mausam.entity.MausamHistory;

@Repository
public interface MausamHistoryRepository extends JpaRepository<MausamHistory, Long>{

}
