package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Person;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * This repository class enables transactional behavior with Redis.
 * Transactional behavior provides the ability to start, commit and
 * rollback Redis transactions.
 */
@Repository
public class TransactionalPersonRedisRepository {

	private final RedisTemplate<String, Person> template;

	public TransactionalPersonRedisRepository(final RedisTemplate<String, Person> template) {
		this.template = template;
	}

	/**
	 * Begins a Redis transaction.
	 */
	public void beginTransaction() {
		this.template.multi();
	}

	/**
	 * Commits a Redis transaction.
	 */
	public void commitTransaction() {
		this.template.exec();
	}

	/**
	 * Rolls back a Redis transaction.
	 */
	public void rollbackTransaction() {
		this.template.discard();
	}
}