package com.batra.spring.reactive.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class SimpleObjectRedisRepository {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ValueOperations<String, Object> valueOperations;

	@Autowired
	public SimpleObjectRedisRepository(final RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.valueOperations = this.redisTemplate.opsForValue();
	}

	public void setObject(final String key, final Object value) {
		this.valueOperations.set(key, value);
	}

	public Object getObject(final String key) {
		return this.valueOperations.get(key);
	}

	public Object deleteObject(final String key) {
		return this.valueOperations.getAndDelete(key);
	}

	public void expireObject(final String key, final Duration timeout) {
		this.redisTemplate.expire(key, timeout);
	}
}