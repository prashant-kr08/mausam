package com.project.mausam.mapper;

import org.springframework.stereotype.Component;

import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.MausamHistory;

@Component
public class EntityMapper {

	public MausamHistory getMausamHistoryFromMausam(final Mausam savedMausam) {
		final MausamHistory mausamHistory = new MausamHistory();
		mausamHistory.setLocation(savedMausam.getLocation());
		mausamHistory.setDataProvider(savedMausam.getDataProvider());
		mausamHistory.setMausamHistory(savedMausam);
		mausamHistory.setProviderUnits(savedMausam.getProviderUnits());
		mausamHistory.setSavingRemarks(savedMausam.getSavingRemarks());
		mausamHistory.setUnitsCode(savedMausam.getUnitsCode());
		mausamHistory.setUpdatedAt(savedMausam.getUpdatedAt());
		mausamHistory.setUpdatedLater(savedMausam.getUpdatedLater());
		mausamHistory.setWeather(savedMausam.getWeather());
		return mausamHistory;
	}

}
