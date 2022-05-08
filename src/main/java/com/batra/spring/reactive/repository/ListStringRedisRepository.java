package com.batra.spring.reactive.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListStringRedisRepository {

	private final StringRedisTemplate template;
	private final ListOperations<String, String> listOperations;

	@Autowired
	public ListStringRedisRepository(final StringRedisTemplate template) {
		this.template = template;
		this.listOperations = this.template.opsForList();
	}

	public void clearList(final String key) {
		this.template.delete(key);
	}

	public List<String> getList(final String key) {
		Long size = this.listOperations.size(key);
		if (size == null)
			return null;
		return this.listOperations.range(key, 0L, size - 1L);
	}

	public void addValueToLeft(final String key, final String value) {
		this.listOperations.leftPush(key, value);
	}

	public void addValueToRight(final String key, final String value) {
		this.listOperations.rightPush(key, value);
	}

	public String removeValueFromLeft(final String key) {
		return this.listOperations.leftPop(key);
	}

	public String removeValueFromRight(final String key) {
		return this.listOperations.rightPop(key);
	}
}