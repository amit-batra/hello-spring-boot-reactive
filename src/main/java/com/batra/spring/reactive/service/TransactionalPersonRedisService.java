package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service enables transactional behavior with Redis.
 */
@Service
public class TransactionalPersonRedisService {

	private final RedisTemplate<String, Person> redisTemplate;

	@Autowired
	public TransactionalPersonRedisService(final RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public List<Object> executeInTransaction(final SessionCallback<List<Object>> session) {
		return this.redisTemplate.execute(session);
	}
}