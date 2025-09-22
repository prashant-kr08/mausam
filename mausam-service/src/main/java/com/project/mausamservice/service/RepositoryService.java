package com.project.mausamservice.service;

import org.springframework.stereotype.Service;

import com.project.mausamservice.entity.Mausam;
import com.project.mausamservice.entity.MausamHistory;
import com.project.mausamservice.entity.MausamUpdateLog;
import com.project.mausamservice.repository.MausamHistoryRepository;
import com.project.mausamservice.repository.MausamRepository;
import com.project.mausamservice.repository.MausamUpdateLogRepository;

import jakarta.transaction.Transactional;

@Service
public class RepositoryService {
	
	MausamRepository mausamRepository;
	MausamHistoryRepository mausamHistoryRepository;
	MausamUpdateLogRepository mausamUpdateLogRepository;
	
	public RepositoryService(MausamRepository mausamRepository, MausamHistoryRepository mausamHistoryRepository, MausamUpdateLogRepository mausamUpdateLogRepository) {
		this.mausamRepository = mausamRepository;
		this.mausamHistoryRepository = mausamHistoryRepository;
		this.mausamUpdateLogRepository = mausamUpdateLogRepository;
	}

	@Transactional
	public void updateMausamOperations(final Mausam latestMausam, final MausamHistory mausamHistory, final MausamUpdateLog mausamUpdatLog) {
		mausamRepository.save(latestMausam);
		final MausamHistory savedMausamHistory = mausamHistoryRepository.save(mausamHistory);
		mausamUpdatLog.setSavedMausamHistory(savedMausamHistory);
		mausamUpdateLogRepository.save(mausamUpdatLog);
	}

}
