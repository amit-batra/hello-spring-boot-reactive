package com.batra.spring.reactive.service;

import com.batra.spring.reactive.repository.TransactionalPersonRedisRepository;
import org.springframework.stereotype.Service;

/**
 * This service enables transactional behavior with Redis through
 * a {@link TransactionalPersonRedisRepository}.
 */
@Service
public class TransactionalPersonRedisService {

	private final TransactionalPersonRedisRepository repository;

	public TransactionalPersonRedisService(final TransactionalPersonRedisRepository repository) {
		this.repository = repository;
	}

	/**
	 * Begins a Redis transaction.
	 */
	public void beginTransaction() {
		repository.beginTransaction();
	}

	/**
	 * Commits a Redis transaction.
	 */
	public void commitTransaction() {
		repository.commitTransaction();
	}

	/**
	 * Rolls back a Redis transaction.
	 */
	public void rollbackTransaction() {
		repository.rollbackTransaction();
	}
}