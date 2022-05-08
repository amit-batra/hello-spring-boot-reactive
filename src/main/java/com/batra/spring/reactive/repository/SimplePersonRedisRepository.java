package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class SimplePersonRedisRepository {

	private final RedisTemplate<String, Person> redisTemplate;
	private final ValueOperations<String, Person> valueOperations;

	@Autowired
	public SimplePersonRedisRepository(final RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.valueOperations = this.redisTemplate.opsForValue();
	}

	public void setPerson(final String key, final Person value) {
		this.valueOperations.set(key, value);
	}

	public Person getPerson(final String key) {
		return this.valueOperations.get(key);
	}

	public Person deletePerson(final String key) {
		return this.valueOperations.getAndDelete(key);
	}

	public void expirePerson(final String key, final Duration timeout) {
		this.redisTemplate.expire(key, timeout);
	}
}