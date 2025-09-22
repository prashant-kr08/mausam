package com.project.mausamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mausamservice.entity.MausamHistory;

@Repository
public interface MausamHistoryRepository extends JpaRepository<MausamHistory, Long>{

}
