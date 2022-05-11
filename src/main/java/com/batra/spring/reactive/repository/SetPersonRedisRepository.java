package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class SetPersonRedisRepository {

	private final RedisTemplate<String, Person> redisTemplate;
	private final SetOperations<String, Person> setOperations;

	public SetPersonRedisRepository(final RedisTemplate<String, Person> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.setOperations = this.redisTemplate.opsForSet();
	}

	public boolean add(final String key, final Person person) {
		final Long nItemsAdded = this.setOperations.add(key, person);
		return nItemsAdded > 0 ? true : false;
	}

	public boolean remove(final String key, final Person person) {
		final Long nItemsRemoved = this.setOperations.remove(key, person);
		return nItemsRemoved > 0 ? true : false;
	}

	public boolean isMember(final String key, final Person person) {
		return this.setOperations.isMember(key, person);
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

	public Set<Person> union(final String key, final String otherKey) {
		return this.setOperations.union(key, otherKey);
	}

	/**
	 * Retrieves the {@link Person} objects in the specified Redis set.
	 * @param key the key referring to a Redis set
	 * @return a {@link Set} of {@link Person} objects in the specified
	 * Redis set
	 */
	public Set<Person> getPeople(final String key) {
		return this.setOperations.members(key);
	}
}