package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.repository.SimpleObjectRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimplePersonRedisService {

	private final SimpleObjectRedisRepository redisRepository;

	@Autowired
	public SimplePersonRedisService(final SimpleObjectRedisRepository redisRepository) {
		this.redisRepository = redisRepository;
	}

	public void setPerson(final String key, final Person value) {
		this.redisRepository.setObject(key, value);
	}

	public Person getPerson(final String key) {
		return (Person) this.redisRepository.getObject(key);
	}
}