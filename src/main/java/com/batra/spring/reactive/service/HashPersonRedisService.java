package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.repository.HashPersonRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This <code>Service</code> deals with manipulating a Redis hash of <code>Person</code>
 * objects using the <code>HashPersonRedisRepository</code> as a delegate.
 * @author Amit Batra
 */
@Service
public class HashPersonRedisService {

	private final HashPersonRedisRepository repository;

	@Autowired
	public HashPersonRedisService(final HashPersonRedisRepository repository) {
		this.repository = repository;
	}

	/**
	 * Determines if a <code>Person</code> object with the specified hash
	 * key exists in the specified Redis hash.
	 *
	 * @param key     the Redis key of the hash that needs to be searched
	 * @param hashKey the key of the <code>Person</code> object whose
	 *                existence needs to be checked
	 * @return <code>true</code> if a <code>Person</code> object with the
	 * specified key was found in the specified Redis hash, <code>false</code>
	 * otherwise.
	 */
	public Boolean containsPerson(String key, String hashKey) {
		return this.repository.containsPerson(key, hashKey);
	}

	/**
	 * Adds the specified <code>Person</code> object to a Redis hash.
	 *
	 * @param key     the Redis key of the hash to which the specified
	 *                <code>Person</code> objects needs to be added
	 * @param hashKey the key with which the <code>Person</code>
	 *                object will be added to the Redis hash
	 * @param person  the <code>Person</code> object that will be
	 *                added to the Redis hash
	 */
	public void addPerson(String key, String hashKey, Person person) {
		repository.addPerson(key, hashKey, person);
	}

	/**
	 * Given a hash key, retrieves a <code>Person</code> object from the
	 * specified Redis hash.
	 *
	 * @param key     the Redis key of the hash that needs to be searched
	 * @param hashKey the key of the <code>Person</code> object that
	 *                needs to be retrieved
	 * @return the <code>Person</code> object corresponding to the
	 * specified hash key in Redis (if found), else <code>null</code>.
	 */
	public Person getPerson(String key, String hashKey) {
		return this.repository.getPerson(key, hashKey);
	}

	/**
	 * Given a hash key, deletes a <code>Person</code> object from the
	 * specified Redis hash.
	 *
	 * @param key     the Redis key of the hash that needs to be searched
	 * @param hashKey the key of the <code>Person</code> object that
	 *                needs to be deleted
	 * @return the <code>true</code> if the <code>Person</code> object
	 * corresponding to the specified hash key in Redis was deleted,
	 * else <code>false</code>.
	 */
	public Boolean deletePerson(String key, String hashKey) {
		return this.repository.deletePerson(key, hashKey);
	}

	/**
	 * Returns a <code>List</code> of all <code>Person</code> objects
	 * stored in the specified Redis hash.
	 *
	 * @param key the key that the Redis hash is bound to
	 * @return a <code>List</code> of <code>Person</code> objects that
	 * were found in the specified Redis hash.
	 */
	public List<Person> getPeople(String key) {
		return this.repository.getPeople(key);
	}

	/**
	 * Returns the count of all <code>Person</code> objects
	 * stored in the specified Redis hash.
	 *
	 * @param key the key that the Redis hash is bound to
	 * @return the count of all <code>Person</code> objects in
	 * the specified Redis hash.
	 */
	public Long size(String key) {
		return this.repository.size(key);
	}
}