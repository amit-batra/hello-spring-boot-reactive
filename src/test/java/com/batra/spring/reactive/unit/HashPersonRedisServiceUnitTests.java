package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.HashPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for the {@link com.batra.spring.reactive.service.HashPersonRedisService}
 * class.
 */
@SpringBootTest
public class HashPersonRedisServiceUnitTests {

	private final HashPersonRedisService service;

	private static final Map<String, Person> PERSON_MAP = Map.ofEntries(
		Map.entry("amit", new Person("Amit Mohan", 25)),
		Map.entry("sumit", new Person("Sumit Mohan", 35)),
		Map.entry("namit", new Person("Namit Mohan", 45))
	);
	private static final String MAP_REDIS_KEY = "personMap";

	@Autowired
	public HashPersonRedisServiceUnitTests(final HashPersonRedisService service) {
		this.service = service;
	}

	@BeforeEach
	public void setup() {
		this.service.clear(MAP_REDIS_KEY);
		PERSON_MAP.keySet().forEach(
			key -> this.service.addPerson(MAP_REDIS_KEY, key, PERSON_MAP.get(key))
		);
	}

	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.service);
	}
}