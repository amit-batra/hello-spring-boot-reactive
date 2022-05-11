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
		return true;
	}

	public boolean remove(final String key, final Person person) {
		return true;
	}

	public boolean isMember(final String key, final Person person) {
		return false;
	}

	public Long size(final String key) {
		return null;
	}

	public Set<Person> difference(final String key, final Set<Person> other) {
		return null;
	}

	public Set<Person> intersection(final String key, final Set<Person> other) {
		return null;
	}

	public Set<Person> union(final String key, final Set<Person> other) {
		return null;
	}

	public Set<Person> getPeople(final String key) {
		return null;
	}
}