package com.batra.spring.reactive.repository;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * This class is used to interface with a Redis server. The hostname and port of the Redis
 * server can be customized in <code>application.properties</code> using these properties:
 * <ul>
 *     <li>spring.redis.host</li>
 *     <li>spring.redis.port</li>
 * </ul>
 */
@Repository
public class SimpleStringRedisRepository {

	private final StringRedisTemplate template;
	private final ValueOperations<String, String> valueOperations;

	/**
	 * Initializes this class with an <code>@Autowired</code> instance of
	 * <code>StringRedisTemplate</code> injected by Spring Boot.
	 * @param template an instance of <code>StringRedisTemplate</code>
	 *                 injected by Spring Boot. Used to perform CRUD
	 *                 operations on Redis.
	 */
	@Autowired
	public SimpleStringRedisRepository(final StringRedisTemplate template) {
		this.template = template;
		this.valueOperations = template.opsForValue();
	}

	/**
	 * Adds a simple string key-value pair to Redis
	 * @param key the key to be used to retrieve the value
	 * @param value the value to be added to Redis
	 */
	public void addSimpleValue(final String key, final String value) {
		this.valueOperations.set(key, value);
	}

	/**
	 * Returns if the specified key and the corresponding value exist in Redis
	 * @param key the key to check for existence
	 * @param value the value that must be stored in Redis against the specified key
	 * @return <code>true</code> if the key exists with the specified value,
	 * <code>false</code> otherwise
	 */
	public boolean checkIfValueExists(final String key, @NotNull final String value) {
		final String retrievedValue = this.valueOperations.get(key);
		return value.equals(retrievedValue);
	}

	/**
	 * Deletes a specified key-value pair from Redis, and returns the corresponding
	 * value.
	 * @param key the key to be deleted
	 * @return if the key was found in Redis, returns the corresponding value, else
	 * returns <code>null</code>
	 */
	public String deleteValue(final String key) {
		return this.valueOperations.getAndDelete(key);
	}

	/**
	 * Deletes all keys (and the corresponding values) from Redis.
	 */
	public void deleteAllValues() {
		this.template.delete(
			Objects.requireNonNull(this.template.keys("*"))
		);
	}

	/**
	 * Adds the specified value against the specified key only if the key is
	 * absent in Redis. Else, leave the existing key-value intact.
	 * @param key the key to be checked in Redis
	 * @param value the value to be saved against the specified key
	 * @return <code>true</code> if the specified value was saved in Redis,
	 * <code>false</code> otherwise.
	 */
	public boolean addIfKeyMissing(final String key, final String value) {
		return Boolean.TRUE.equals(this.valueOperations.setIfAbsent(key, value));
	}

	/**
	 * Returns the value for the specified key (if exists).
	 * @param key the key to be looked up in Redis
	 * @return the value against the specified key (if found),
	 * <code>null</code> otherwise.
	 */
	public String getValueForKey(final String key) {
		return this.valueOperations.get(key);
	}

	public Long incrementValue(final String key) {
		return this.valueOperations.increment(key);
	}
}