package com.project.mausam.service;

import org.springframework.stereotype.Service;

import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.MausamHistory;
import com.project.mausam.entity.MausamUpdateLog;
import com.project.mausam.repository.MausamHistoryRepository;
import com.project.mausam.repository.MausamRepository;
import com.project.mausam.repository.MausamUpdateLogRepository;

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
