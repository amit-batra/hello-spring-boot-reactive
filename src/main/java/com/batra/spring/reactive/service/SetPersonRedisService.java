package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.repository.SetPersonRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * This service class helps manipulate Redis sets of
 * {@link Person} objects using a {@link SetPersonRedisRepository}.
 */
@Service
public class SetPersonRedisService {

	private final SetPersonRedisRepository repository;

	@Autowired
	public SetPersonRedisService(final SetPersonRedisRepository repository) {
		this.repository = repository;
	}

	/**
	 * Adds a {@link Person} object to a Redis set.
	 *
	 * @param key    the key pointing to a Redis set
	 * @param person the {@link Person} object that
	 *               needs to be added to the Redis set
	 * @return <code>true</code> if the specified {@link Person}
	 * object was added to the Redis set, <code>false</code>
	 * otherwise.
	 */
	public boolean add(String key, Person person) {
		return this.repository.add(key, person);
	}

	/**
	 * Removes a {@link Person} object from a Redis set.
	 *
	 * @param key    the key pointing to the Redis set
	 * @param person the {@link Person} object to be removed
	 *               from the Redis set
	 * @return <code>true</code> if the specified {@link Person}
	 * object was removed from the Redis set, <code>false</code>
	 * otherwise.
	 */
	public boolean remove(String key, Person person) {
		return this.repository.remove(key, person);
	}

	/**
	 * Confirms whether a given {@link Person} object a member of the
	 * specified Redis set.
	 *
	 * @param key    the key referring to a Redis set
	 * @param person the {@link Person} object whose Set membership
	 *               needs to be confirmed
	 * @return <code>true</code> if the give {@link Person} object is
	 * a member of the Redis set, <code>false</code> otherwise.
	 */
	public boolean isMember(String key, Person person) {
		return this.repository.isMember(key, person);
	}

	/**
	 * Returns the number of elements in a Redis set.
	 *
	 * @param key the key referring to a Redis set
	 * @return the number of elements in the specified
	 * Redis set.
	 */
	public Long size(String key) {
		return this.repository.size(key);
	}

	/**
	 * Computes the difference of two Redis sets and returns the
	 * resulting set without storing it in Redis.
	 *
	 * @param key      the key referring to the first Redis set
	 * @param otherKey the key referring to the second Redis set
	 * @return a {@link Set} resulting from the difference of
	 * the two Redis sets.
	 */
	public Set<Person> difference(String key, String otherKey) {
		return this.repository.difference(key, otherKey);
	}

	/**
	 * Performs an intersection of two Redis sets and returns the
	 * resulting set without storing it in Redis.
	 *
	 * @param key      the key referring to the first Redis set
	 * @param otherKey the key referring to the second Redis set
	 * @return a {@link Set} resulting from the intersection of
	 * the two Redis sets.
	 */
	public Set<Person> intersection(String key, String otherKey) {
		return this.repository.intersection(key, otherKey);
	}

	/**
	 * Performs a union of two Redis sets and returns the resulting
	 * set without storing it in Redis.
	 *
	 * @param key      the key referring to the first Redis set
	 * @param otherKey the key referring to the second Redis set
	 * @return a {@link Set} resulting from the union of the two
	 * Redis sets.
	 */
	public Set<Person> union(String key, String otherKey) {
		return this.repository.union(key, otherKey);
	}

	/**
	 * Retrieves the {@link Person} objects in the specified Redis set.
	 *
	 * @param key the key referring to a Redis set
	 * @return a {@link Set} of {@link Person} objects in the specified
	 * Redis set.
	 */
	public Set<Person> getPeople(String key) {
		return this.repository.getPeople(key);
	}
}