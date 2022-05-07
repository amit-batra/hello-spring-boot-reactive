package com.batra.spring.reactive.service;

import com.batra.spring.reactive.repository.SimpleStringRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleStringRedisService {

	private final SimpleStringRedisRepository redisRepository;

	@Autowired
	public SimpleStringRedisService(SimpleStringRedisRepository redisRepository) {
		this.redisRepository = redisRepository;
	}

	public void setValue(final String key, final String value) {
		this.redisRepository.addSimpleValue(key, value);
	}

	public boolean checkIfValueExists(final String key, final String value) {
		return this.redisRepository.checkIfValueExists(key, value);
	}

	public String deleteValue(final String key) {
		return this.redisRepository.deleteValue(key);
	}

	public void deleteAllValues() {
		this.redisRepository.deleteAllValues();
	}

	public boolean addIfKeyMissing(final String key, final String value) {
		return this.redisRepository.addIfKeyMissing(key, value);
	}

	public String getValueForKey(final String key) {
		return this.redisRepository.getValueForKey(key);
	}

	public Long incrementValue(final String key) {
		return this.redisRepository.incrementValue(key);
	}
}