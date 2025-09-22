package com.project.mausamservice.service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.project.mausamservice.api.dto.getcitymausam.CityMausamRequest;
import com.project.mausamservice.api.dto.getcitymausam.CityMausamResponse;
import com.project.mausamservice.api.dto.getcitymausam.Trace;
import com.project.mausamservice.api.dto.getmulticitymausam.MultiCityMausamRequest;
import com.project.mausamservice.api.dto.getmulticitymausam.MultiCityMausamResponse;
import com.project.mausamservice.api.dto.savecitymausam.SaveCityMausamRequest;
import com.project.mausamservice.api.dto.savecitymausam.SaveCityMausamResponse;
import com.project.mausamservice.api.dto.updatecitymausam.UpdateCityMausamResponse;
import com.project.mausamservice.entity.Mausam;
import com.project.mausamservice.entity.MausamHistory;
import com.project.mausamservice.entity.MausamUpdateLog;
import com.project.mausamservice.mapper.EntityMapper;
import com.project.mausamservice.mapper.MausamRequestMapper;
import com.project.mausamservice.mapper.MausamResponseMapper;
import com.project.mausamservice.repository.MausamRepository;
import com.project.mausamservice.utility.MausamConstants;
import com.project.mausamservice.utility.RedisCacheUtil;
import com.project.mausamservice.utility.errorhandling.CacheDataNotFoundException;
import com.project.mausamservice.utility.errorhandling.InvalidIdFormatException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class MausamService {
	
	WeatherApiService weatherApiService;
	MausamResponseMapper mausamResponseMapper;
	MausamRequestMapper mausamRequestMapper;
	MausamRepository mausamRepository;
	RedisCacheUtil redisCacheUtil;
	RepositoryService repositoryService;
	EntityMapper entityMapper;
	ApplicationContext applicationContext;
	MausamAsyncService mausamAsyncService;
	@PersistenceContext
	EntityManager entityManager;
	
	public MausamService(WeatherApiService weatherApiService, MausamResponseMapper mausamResponseMapper,
			MausamRequestMapper mausamRequestMapper, MausamRepository mausamRepository, RedisCacheUtil redisCacheUtil,
			RepositoryService repositoryService, EntityMapper entityMapper, ApplicationContext applicationContext,
			@Lazy MausamAsyncService mausamAsyncService) {
		this.weatherApiService = weatherApiService;
		this.mausamResponseMapper = mausamResponseMapper;
		this.mausamRequestMapper = mausamRequestMapper;
		this.mausamRepository = mausamRepository;
		this.redisCacheUtil = redisCacheUtil;
		this.repositoryService = repositoryService;
		this.entityMapper = entityMapper;
		this.applicationContext = applicationContext;
		this.mausamAsyncService = mausamAsyncService;
		System.out.println("MausamService init");
	}
	
	private MausamService self() {
		return applicationContext.getBean(MausamService.class);
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
		return mausamResponseMapper.getSaveCityMausamResponse(savedMausam);
		
	}

	@Cacheable(value = MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, key = "'saved_' + #id")
	public List<SaveCityMausamResponse> getSavedCityWeatherById(final String id) {
		
		List<SaveCityMausamResponse> savedCityMausamResponse = new LinkedList<>();
		if("all".equalsIgnoreCase(id)) {
			final List<Mausam> lastTenRecords = mausamRepository.findTop10ByOrderByWeather_DateTimeDesc();
//			List<Mausam> lastTenRecords = mausamRepository
//			        .findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "dateTime")))
//			        .getContent();			
			return lastTenRecords.stream().map(mausam -> (mausamResponseMapper.getSaveCityMausamResponse(mausam))).toList();
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
		entityManager.detach(savedMausam);
		final CityMausamResponse latestCityWeather = self().getCityWeather(cityMausamRequestByMausam);
		final Trace trace = latestCityWeather.getTrace();
		final LocalDateTime currentDateTime = LocalDateTime.now();
		final Mausam latestMausam = (Mausam) redisCacheUtil.getCacheData(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, trace.getId());
		latestMausam.setUpdatedLater(true);
		latestMausam.setUpdatedAt(currentDateTime);
		latestMausam.setSavingRemarks(String.join("|", "Updated", savedMausam.getSavingRemarks()));
		latestMausam.setId(savedMausam.getId());

		final MausamHistory mausamHistory = entityMapper.getMausamHistoryFromMausam(savedMausam);
		
		final MausamUpdateLog mausamUpdatLog = new MausamUpdateLog();
		mausamUpdatLog.setUpdateTime(currentDateTime);
		
		final SaveCityMausamResponse pastRecord = mausamResponseMapper.getSaveCityMausamResponse(savedMausam);
		repositoryService.updateMausamOperations(latestMausam, mausamHistory, mausamUpdatLog);
		final SaveCityMausamResponse presentRecord = mausamResponseMapper.getSaveCityMausamResponse(latestMausam);
		
		final UpdateCityMausamResponse updateCityMausamResponse = new UpdateCityMausamResponse();
		updateCityMausamResponse.setPastRecord(pastRecord);
		updateCityMausamResponse.setPresentRecord(presentRecord);
		
		return updateCityMausamResponse;
	}

	public void deleteSavedCityWeatherById(final Long id) {
		mausamRepository.deleteById(id);
	}

	public MultiCityMausamResponse getMultiCityWeather(MultiCityMausamRequest multiCityMausamRequest) {
		final List<String> cities = multiCityMausamRequest.getCities();
		final int units = multiCityMausamRequest.getUnits();
		final Map<String, CityMausamResponse> cityWiseResponse = new ConcurrentHashMap<>();   // To maintain order :-  Collections.synchronizedMap(new LinkedHashMap<>())

		final List<CompletableFuture<Void>> responses = cities.stream().map(city -> {
			CityMausamRequest cityMausamRequest = new CityMausamRequest();
			cityMausamRequest.setCityName(city);
			cityMausamRequest.setUnits(units);
			return mausamAsyncService.getCityWeatherAsync(cityMausamRequest).thenAccept(response -> cityWiseResponse.put(city, response));
		}).toList();
		CompletableFuture.allOf(responses.toArray(new CompletableFuture[0])).join();

		return mausamResponseMapper.getMultiCityMausamResponse(cities, cityWiseResponse);
	}

}
