package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This repository deals with <code>Lists</code> of <code>Persons</code>
 * in Redis.
 */
@Repository
public class ListPersonRedisRepository {

	private final RedisTemplate<String, Person> redisTemplate;
	private final ListOperations<String, Person> listOperations;

	@Autowired
	public ListPersonRedisRepository(final RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.listOperations = this.redisTemplate.opsForList();
	}

	/**
	 * Clears the Redis list with the specified key.
	 * @param key the key of the Redis list to be cleared.
	 * @return <code>true</code> if the Redis list specified by the key was
	 * cleared successfully, else returns <code>false</code>.
	 */
	public Boolean clearList(final String key) {
		return this.redisTemplate.delete(key);
	}

	/**
	 * Returns the size of the Redis list with the specified key.
	 * @param key the key of the Redis list whose size needs to be determined.
	 * @return the size of the Redis list specified by the key (if exists),
	 * else returns zero.
	 */
	public Long size(final String key) {
		return this.listOperations.size(key);
	}

	/**
	 * Returns the list of <code>Persons</code> in the Redis list with the
	 * specified key.
	 * @param key the key of the Redis list that needs to be fetched.
	 * @return the list retrieved from Redis (if exists), else returns an
	 * empty list.
	 */
	public List<Person> getList(final String key) {
		final Long size = this.listOperations.size(key);
		if (size == null) {
			return null;
		}
		return this.listOperations.range(key, 0,  size - 1);
	}
}