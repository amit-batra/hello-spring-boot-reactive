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
		return this.hashOperations.hasKey(key, hashKey);
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
		this.hashOperations.put(key, hashKey, person);
	}

	/**
	 * Given a hash key, retrieves a <code>Person</code> object from the
	 * specified Redis hash.
	 * @param key the Redis key of the hash that needs to be searched
	 * @param hashKey the key of the <code>Person</code> object that
	 *                needs to be retrieved
	 * @return the <code>Person</code> object corresponding to the
	 * specified hash key in Redis (if found), else <code>null</code>.
	 */
	public Person getPerson(final String key, final String hashKey) {
		return this.hashOperations.get(key, hashKey);
	}

	/**
	 * Given a hash key, deletes a <code>Person</code> object from the
	 * specified Redis hash.
	 * @param key the Redis key of the hash that needs to be searched
	 * @param hashKey the key of the <code>Person</code> object that
	 *                needs to be deleted
	 * @return the <code>true</code> if the <code>Person</code> object
	 * corresponding to the specified hash key in Redis was deleted,
	 * else <code>false</code>.
	 */
	public Boolean deletePerson(final String key, final String hashKey) {
		final Long count = this.hashOperations.delete(key, hashKey);
		return count.longValue() == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * Returns a <code>List</code> of all <code>Person</code> objects
	 * stored in the specified Redis hash.
	 * @param key the key that the Redis hash is bound to
	 * @return a <code>List</code> of <code>Person</code> objects that
	 * were found in the specified Redis hash.
	 */
	public List<Person> getPeople(final String key) {
		return this.hashOperations.values(key);
	}

	/**
	 * Returns the count of all <code>Person</code> objects
	 * stored in the specified Redis hash.
	 * @param key the key that the Redis hash is bound to
	 * @return the count of all <code>Person</code> objects in
	 * the specified Redis hash.
	 */
	public Long size(final String key) {
		return this.hashOperations.size(key);
	}

	/**
	 * Clears the Redis hash with the specified key.
	 * @param key the key pointing to the Redis hash to be cleared
	 */
	public void clear(final String key) {
		this.redisTemplate.delete(key);
	}
}