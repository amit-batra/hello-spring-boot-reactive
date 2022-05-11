package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.HashPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link HashPersonRedisService} class.
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

	/**
	 * Executed before each test case. Re-initializes the Redis hash with the
	 * Java <code>Map</code>.
	 */
	@BeforeEach
	public void setup() {
		this.service.clear(MAP_REDIS_KEY);
		PERSON_MAP.keySet().forEach(
			key -> this.service.addPerson(MAP_REDIS_KEY, key, PERSON_MAP.get(key))
		);
	}

	/**
	 * Validates that the Spring Boot auto-wiring was able to successfully
	 * inject the {@link HashPersonRedisService} instance.
	 */
	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.service);
	}

	/**
	 * Validates the <code>containsPerson</code> method in class
	 * {@link HashPersonRedisService}. It does that by checking for the
	 * existence of all keys (from the Java map) in the Redis hash.
	 */
	@Test
	public void testContainsPerson() {
		PERSON_MAP.keySet().forEach(key ->
			assertTrue(this.service.containsPerson(MAP_REDIS_KEY, key))
		);
	}
}