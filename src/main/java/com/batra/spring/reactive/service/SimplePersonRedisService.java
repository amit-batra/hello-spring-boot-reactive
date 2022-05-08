package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.repository.SimplePersonRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SimplePersonRedisService {

	private final SimplePersonRedisRepository redisRepository;

	@Autowired
	public SimplePersonRedisService(final SimplePersonRedisRepository redisRepository) {
		this.redisRepository = redisRepository;
	}

	public void setPerson(final String key, final Person value) {
		this.redisRepository.setPerson(key, value);
	}

	public Person getPerson(final String key) {
		return this.redisRepository.getPerson(key);
	}

	public Person deletePerson(final String key) {
		return this.redisRepository.deletePerson(key);
	}

	public void expirePerson(final String key, final Duration timeout) {
		this.redisRepository.expirePerson(key, timeout);
	}
}