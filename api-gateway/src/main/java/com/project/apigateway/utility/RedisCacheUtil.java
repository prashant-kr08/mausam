package com.project.apigateway.utility;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheUtil {
	
	RedisTemplate<String, Object> redisTemplate;
	
	public RedisCacheUtil(RedisTemplate<String, Object> redisTemplate) {
		System.out.println("RedisCacheUtil init.");
		this.redisTemplate = redisTemplate;
	}
	
	public void addToCacheWithTtlInMinutes(final String namespace, final String key, final Object value, final int ttl) {
		redisTemplate.opsForValue().set(getCacheKeyWithNamespace(namespace, key), value, ttl, TimeUnit.MINUTES);
	}
	
	public Object getCacheData(final String namespace, final String Key) {
		return redisTemplate.opsForValue().get(getCacheKeyWithNamespace(namespace, Key));
	}
	
	public void deleteCacheData(final String namespace, final String key) {
		redisTemplate.delete(getCacheKeyWithNamespace(namespace, key));
	}

	private String getCacheKeyWithNamespace(final String namespace, final String key) {
		if(!(namespace == null || namespace.isBlank())) {
			return String.join("::", namespace, key);
		}
		return key;
	}
}
