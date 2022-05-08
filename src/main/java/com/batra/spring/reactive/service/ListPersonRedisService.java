package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.repository.ListPersonRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListPersonRedisService {

	private final ListPersonRedisRepository repository;

	@Autowired
	public ListPersonRedisService(final ListPersonRedisRepository repository) {
		this.repository = repository;
	}

	public Boolean clearList(final String key) {
		return repository.clearList(key);
	}

	public Long size(final String key) {
		return repository.size(key);
	}

	public List<Person> getList(final String key) {
		return repository.getList(key);
	}

	public void addPersonToLeft(final String key, final Person person) {
		repository.addPersonToLeft(key, person);
	}

	public void addPersonToRight(final String key, final Person person) {
		repository.addPersonToRight(key, person);
	}

	public Person removePersonFromLeft(final String key) {
		return repository.removePersonFromLeft(key);
	}

	public Person removePersonFromRight(final String key) {
		return repository.removePersonFromRight(key);
	}
}