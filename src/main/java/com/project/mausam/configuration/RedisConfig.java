package com.project.mausam.configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.mausam.utility.MausamConstants;

@Configuration
public class RedisConfig {
	
	ObjectMapper redisObjectMapper;
	
	public RedisConfig(@Qualifier("redisObjectMapper") ObjectMapper redisOjectMapper) {
		this.redisObjectMapper = redisOjectMapper;
		System.out.println("redisConfig init." + redisOjectMapper.hashCode());
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		System.out.println("redisObjectMapper in cachemanager : "+ redisObjectMapper.hashCode());
		RedisCacheConfiguration jsonConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(MausamConstants.MAUSAM_CACHE_DEFAULT_EXPIRY_TIME_IN_MINUTES))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper)));

		Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
		cacheConfigs.put(MausamConstants.MAUSAM_JSON_CACHE_WITH_EXPIRY_NAMESPACE, jsonConfig.entryTtl(Duration.ofMinutes(MausamConstants.MAUSAM_CACHE_EXPIRY_TIME_IN_MINUTES))); 
		cacheConfigs.put(MausamConstants.MAUSAM_JSON_CACHE_NAMESPACE, jsonConfig); 

		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(jsonConfig)
				.withInitialCacheConfigurations(cacheConfigs).build();
	}
	
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper));
        return template;
    }
    
}
