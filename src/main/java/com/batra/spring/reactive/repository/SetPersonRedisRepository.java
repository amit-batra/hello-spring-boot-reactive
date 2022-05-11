package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SetPersonRedisRepository {

	private final SetOperations<String, Person> setOperations;

	@Autowired
	public SetPersonRedisRepository(final RedisTemplate<String, Person> redisTemplate) {
		this.setOperations = redisTemplate.opsForSet();
	}

	public boolean add(final String key, final Person person) {
		final Long nItemsAdded = this.setOperations.add(key, person);
		return (nItemsAdded != null) && (nItemsAdded > 0);
	}

	public boolean remove(final String key, final Person person) {
		final Long nItemsRemoved = this.setOperations.remove(key, person);
		return (nItemsRemoved != null) && (nItemsRemoved > 0);
	}

	public boolean isMember(final String key, final Person person) {
		final Boolean result = this.setOperations.isMember(key, person);
		return (result != null) && result;
	}

	public Long size(final String key) {
		return this.setOperations.size(key);
	}

	public Set<Person> difference(final String key, final String otherKey) {
		return this.setOperations.difference(key, otherKey);
	}

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
}