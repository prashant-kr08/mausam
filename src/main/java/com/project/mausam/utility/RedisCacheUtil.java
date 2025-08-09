package com.project.mausam.utility;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheUtil {
	
	RedisTemplate<String, Object> redisTemplate;
	
	public RedisCacheUtil(RedisTemplate<String, Object> redisTemplate) {
		System.out.println("RedisCacheUtil init.");
		this.redisTemplate = redisTemplate;
	}
	
	public void addToCacheWithTtlInMinutes(final String namespace, final String key, final Object value, final int ttl) {
		getCacheKeyWithNamespace(namespace, key);
		redisTemplate.opsForValue().set(getCacheKeyWithNamespace(namespace, key), value, ttl, TimeUnit.MINUTES);
	}

	private String getCacheKeyWithNamespace(final String namespace, final String key) {
		if(!(namespace == null || namespace.isBlank())) {
			return String.join("::", namespace, key);
		}
		return key;
	}
}
