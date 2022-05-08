package com.batra.spring.reactive.service;

import com.batra.spring.reactive.repository.ListStringRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListStringRedisService {

	private final ListStringRedisRepository repository;

	@Autowired
	public ListStringRedisService(final ListStringRedisRepository repository) {
		this.repository = repository;
	}

	public void clearList(final String key) {
		this.repository.clearList(key);
	}

	public List<String> getList(final String key) {
		return this.repository.getList(key);
	}

	public void addValueToLeft(final String key, final String value) {
		this.repository.addValueToLeft(key, value);
	}

	public void addValueToRight(final String key, final String value) {
		this.repository.addValueToRight(key, value);
	}

	public String removeValueFromLeft(final String key) {
		return this.repository.removeValueFromLeft(key);
	}

	public String removeValueFromRight(final String key) {
		return this.repository.removeValueFromRight(key);
	}
}