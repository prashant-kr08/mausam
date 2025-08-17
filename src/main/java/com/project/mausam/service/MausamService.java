package com.project.mausam.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.project.mausam.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausam.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausam.api.dto.getcitymausam.Trace;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamRequest;
import com.project.mausam.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausam.api.dto.updatecitymausam.UpdateCityMausamResponse;
import com.project.mausam.entity.Mausam;
import com.project.mausam.entity.MausamHistory;
import com.project.mausam.entity.MausamUpdateLog;
import com.project.mausam.mapper.MausamRequestMapper;
import com.project.mausam.mapper.MausamResponseMapper;
import com.project.mausam.repository.MausamRepository;
import com.project.mausam.utility.MausamConstants;
import com.project.mausam.utility.RedisCacheUtil;
import com.project.mausam.utility.errorhandling.CacheDataNotFoundException;
import com.project.mausam.utility.errorhandling.InvalidIdFormatException;

@Service
public class MausamService {
	
	WeatherApiService weatherApiService;
	MausamResponseMapper mausamResponseMapper;
	MausamRequestMapper mausamRequestMapper;
	MausamRepository mausamRepository;
	RedisCacheUtil redisCacheUtil;
	RepositoryService repositoryService;
	
	public MausamService(WeatherApiService weatherApiService, MausamResponseMapper mausamResponseMapper,
			MausamRequestMapper mausamRequestMapper, MausamRepository mausamRepository, RedisCacheUtil redisCacheUtil,
			RepositoryService repositoryService) {
		this.weatherApiService = weatherApiService;
		this.mausamResponseMapper = mausamResponseMapper;
		this.mausamRequestMapper = mausamRequestMapper;
		this.mausamRepository = mausamRepository;
		this.redisCacheUtil = redisCacheUtil;
		this.repositoryService = repositoryService;
		System.out.println("MausamService init");
	}
	
	@Cacheable(value = MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, key = "#cityMausamRequest.cityName + '_' + #cityMausamRequest.units")
	public CityMausamResponse getCityWeather(final CityMausamRequest cityMausamRequest) {
		final Mausam cityMausam = weatherApiService.getCityWeather(cityMausamRequest);
		final CityMausamResponse cityMausamResponse = mausamResponseMapper.getCityMausamResponse(cityMausam);
		final Trace trace = cityMausamResponse.getTrace();
		redisCacheUtil.addToCacheWithTtlInMinutes(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, trace.getId(),
				cityMausam, trace.getExpiryInMinutes());
		return cityMausamResponse;
	}

	
//	@Cacheable(value = MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, key = "'saved_' + #saveCityMausamRequest.traceId")
	public SaveCityMausamResponse saveCityWeather(final SaveCityMausamRequest saveCityMausamRequest) {
		final String traceId = saveCityMausamRequest.getTraceId();
		final Mausam cachedMausamData = (Mausam) redisCacheUtil.getCacheData(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, traceId);
		if (null == cachedMausamData) {
			throw new CacheDataNotFoundException();
		} else {
			cachedMausamData.setSavingRemarks(saveCityMausamRequest.getSavingRemarks());
		}
		final Mausam savedMausam = mausamRepository.save(cachedMausamData);
		final SaveCityMausamResponse saveCityMausamResponse = mausamResponseMapper.getSaveCityMausamResponse(savedMausam);
		
		return saveCityMausamResponse;
	}

	@Cacheable(value = MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, key = "'saved_' + #id")
	public List<SaveCityMausamResponse> getSavedCityWeatherById(final String id) {
		
		List<SaveCityMausamResponse> savedCityMausamResponse = new LinkedList<>();
		if("all".equalsIgnoreCase(id)) {
			final List<Mausam> lastTenRecords = mausamRepository.findTop10ByOrderByWeather_DateTimeDesc();
//			List<Mausam> lastTenRecords = mausamRepository
//			        .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "dateTime")))
//			        .getContent();			
			return lastTenRecords.stream().map(mausam -> (mausamResponseMapper.getSaveCityMausamResponse(mausam))).collect(Collectors.toList());
		} else if (!id.matches("\\d+")) {
            throw new InvalidIdFormatException("ID must be a number or 'all'");
        }
		
		final Mausam savedMausam = mausamRepository.getReferenceById(Long.parseLong(id));
		savedCityMausamResponse.add(mausamResponseMapper.getSaveCityMausamResponse(savedMausam));
		return savedCityMausamResponse;
	}

	public UpdateCityMausamResponse updateSavedCityWeatherToLatestById(final Long id) {
		final Mausam savedMausam = mausamRepository.getReferenceById(id);
		final CityMausamRequest cityMausamRequestByMausam = mausamRequestMapper.getCityMausamRequestByMausam(savedMausam);
		final CityMausamResponse latestCityWeather = getCityWeather(cityMausamRequestByMausam);
		final Trace trace = latestCityWeather.getTrace();
		final LocalDateTime currentDateTime = LocalDateTime.now();
		final Mausam latestMausam = (Mausam) redisCacheUtil.getCacheData(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, trace.getId());
		latestMausam.setUpdatedLater(true);
		latestMausam.setUpdatedAt(currentDateTime);
		latestMausam.setSavingRemarks(String.join("|", "Updated", savedMausam.getSavingRemarks()));
		latestMausam.setId(savedMausam.getId());

		final MausamHistory mausamHistory = new MausamHistory();
		mausamHistory.setMausamHistory(savedMausam);
		
		final MausamUpdateLog mausamUpdatLog = new MausamUpdateLog();
		mausamUpdatLog.setUpdateTime(currentDateTime);
		mausamUpdatLog.setOldMausam(savedMausam);
		
		repositoryService.updateMausamOperations(latestMausam, mausamHistory, mausamUpdatLog);
		
		final SaveCityMausamResponse pastRecord = mausamResponseMapper.getSaveCityMausamResponse(savedMausam);
		final SaveCityMausamResponse presentRecord = mausamResponseMapper.getSaveCityMausamResponse(latestMausam);
		
		final UpdateCityMausamResponse updateCityMausamResponse = new UpdateCityMausamResponse();
		updateCityMausamResponse.setPastRecord(pastRecord);
		updateCityMausamResponse.setPresentRecord(presentRecord);
		
		return updateCityMausamResponse;
	}

	
}
