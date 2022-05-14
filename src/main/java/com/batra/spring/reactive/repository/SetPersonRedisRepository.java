package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * This repository class helps manipulate Redis sets of
 * {@link Person} objects.
 */
@Repository
public class SetPersonRedisRepository {

	private final RedisTemplate<String, Person> redisTemplate;
	private final SetOperations<String, Person> setOperations;

	@Autowired
	public SetPersonRedisRepository(final RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.setOperations = this.redisTemplate.opsForSet();
	}

	/**
	 * Adds a {@link Person} object to a Redis set.
	 * @param key the key pointing to a Redis set
	 * @param person the {@link Person} object that
	 *               needs to be added to the Redis set
	 * @return <code>true</code> if the specified {@link Person}
	 * object was added to the Redis set, <code>false</code>
	 * otherwise.
	 */
	public boolean add(final String key, final Person person) {
		final Long nItemsAdded = this.setOperations.add(key, person);
		return (nItemsAdded != null) && (nItemsAdded > 0);
	}

	/**
	 * Removes a {@link Person} object from a Redis set.
	 * @param key the key pointing to the Redis set
	 * @param person the {@link Person} object to be removed
	 *               from the Redis set
	 * @return <code>true</code> if the specified {@link Person}
	 * object was removed from the Redis set, <code>false</code>
	 * otherwise.
	 */
	public boolean remove(final String key, final Person person) {
		final Long nItemsRemoved = this.setOperations.remove(key, person);
		return (nItemsRemoved != null) && (nItemsRemoved > 0);
	}

	/**
	 * Confirms whether a given {@link Person} object a member of the
	 * specified Redis set.
	 * @param key the key referring to a Redis set
	 * @param person the {@link Person} object whose Set membership
	 *               needs to be confirmed
	 * @return <code>true</code> if the give {@link Person} object is
	 * a member of the Redis set, <code>false</code> otherwise.
	 */
	public boolean isMember(final String key, final Person person) {
		final Boolean result = this.setOperations.isMember(key, person);
		return (result != null) && result;
	}

	/**
	 * Returns the number of elements in a Redis set.
	 * @param key the key referring to a Redis set
	 * @return the number of elements in the specified
	 * Redis set.
	 */
	public Long size(final String key) {
		return this.setOperations.size(key);
	}

	/**
	 * Computes the difference of two Redis sets and returns the
	 * resulting set without storing it in Redis.
	 * @param key the key referring to the first Redis set
	 * @param otherKey the key referring to the second Redis set
	 * @return a {@link Set} resulting from the difference of
	 * the two Redis sets.
	 */
	public Set<Person> difference(final String key, final String otherKey) {
		return this.setOperations.difference(key, otherKey);
	}

	/**
	 * Performs an intersection of two Redis sets and returns the
	 * resulting set without storing it in Redis.
	 * @param key the key referring to the first Redis set
	 * @param otherKey the key referring to the second Redis set
	 * @return a {@link Set} resulting from the intersection of
	 * the two Redis sets.
	 */
	public Set<Person> intersection(final String key, final String otherKey) {
		return this.setOperations.intersect(key, otherKey);
	}

	/**
	 * Performs a union of two Redis sets and returns the resulting
	 * set without storing it in Redis.
	 * @param key the key referring to the first Redis set
	 * @param otherKey the key referring to the second Redis set
	 * @return a {@link Set} resulting from the union of the two
	 * Redis sets.
	 */
	public Set<Person> union(final String key, final String otherKey) {
		return this.setOperations.union(key, otherKey);
	}

	/**
	 * Retrieves the {@link Person} objects in the specified Redis set.
	 * @param key the key referring to a Redis set
	 * @return a {@link Set} of {@link Person} objects in the specified
	 * Redis set.
	 */
	public Set<Person> getPeople(final String key) {
		return this.setOperations.members(key);
	}

	/**
	 * Clears the Redis set referenced by the specified
	 * Redis key.
	 * @param key the key referring to the Redis set that
	 *            needs to be cleared
	 */
	public void clear(String key) {
		this.redisTemplate.delete(key);
	}
}