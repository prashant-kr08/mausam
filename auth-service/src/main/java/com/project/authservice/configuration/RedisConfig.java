package com.project.authservice.configuration;

import com.project.authservice.utility.AuthConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration jsonConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(AuthConstants.AUTH_CACHE_DEFAULT_EXPIRY_TIME_IN_MINUTES))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
		cacheConfigs.put(AuthConstants.AUTH_CACHE_WITH_EXPIRY_NAMESPACE, jsonConfig.entryTtl(Duration.ofMillis(AuthConstants.AUTH_JWT_EXPIRY_TIME_IN_MILLIS)));
		cacheConfigs.put(AuthConstants.AUTH_CACHE_NAMESPACE, jsonConfig);

		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(jsonConfig)
				.withInitialCacheConfigurations(cacheConfigs).build();
	}
    
}
