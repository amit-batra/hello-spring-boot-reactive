package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class deals with manipulating a Redis hash of <code>Person</code>
 * objects.
 * @author Amit Batra
 */
@Repository
public class HashPersonRedisRepository {

	private final RedisTemplate<String, Person> redisTemplate;
	private final HashOperations<String, String, Person> hashOperations;

	@Autowired
	public HashPersonRedisRepository(final RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = this.redisTemplate.opsForHash();
	}

	/**
	 * Determines if a <code>Person</code> object with the specified hash
	 * key exists in the specified Redis hash.
	 * @param key the Redis key of the hash that needs to be searched
	 * @param hashKey the key of the <code>Person</code> object whose
	 *                existence needs to be checked
	 * @return <code>true</code> if a <code>Person</code> object with the
	 * specified key was found in the specified Redis hash, <code>false</code>
	 * otherwise.
	 */
	public Boolean containsPerson(final String key, final String hashKey) {
		return false;
	}

	/**
	 * Adds the specified <code>Person</code> object to a Redis hash.
	 * @param key the Redis key of the hash to which the specified
	 *            <code>Person</code> objects needs to be added
	 * @param hashKey the key with which the <code>Person</code>
	 *                object will be added to the Redis hash
	 * @param person the <code>Person</code> object that will be
	 *               added to the Redis hash
	 */
	public void addPerson(final String key, final String hashKey, final Person person) {

	}

	public Person getPerson(final String key, final String hashKey) {
		return null;
	}

	public Person deletePerson(final String key, final String hashKey) {
		return null;
	}

	public List<Person> getPeople(final String key) {
		return null;
	}

	public Long size(final String key) {
		return null;
	}
}