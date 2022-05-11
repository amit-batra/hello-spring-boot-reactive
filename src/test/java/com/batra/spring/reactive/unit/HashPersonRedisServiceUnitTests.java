package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.HashPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link HashPersonRedisService} class.
 */
@SpringBootTest
public class HashPersonRedisServiceUnitTests {

	private final HashPersonRedisService service;

	private static final String REDIS_HASH_KEY = "personMap";
	private static final Map<String, Person> PERSON_MAP = Map.ofEntries(
		Map.entry("amit", new Person("Amit Mohan", 25)),
		Map.entry("sumit", new Person("Sumit Mohan", 35)),
		Map.entry("namit", new Person("Namit Mohan", 45))
	);

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
		this.service.clear(REDIS_HASH_KEY);
		PERSON_MAP.keySet().forEach(
			key -> this.service.addPerson(REDIS_HASH_KEY, key, PERSON_MAP.get(key))
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
			assertTrue(this.service.containsPerson(REDIS_HASH_KEY, key))
		);
	}

	/**
	 * Validates the <code>addPerson</code> method in class
	 * {@link HashPersonRedisService}. It does that by comparing
	 * the before and after state of the Redis hash.
	 */
	@Test
	public void testAddPerson() {

		final List<Person> beforeList = this.service.getPeople(REDIS_HASH_KEY);
		final int beforeSize = beforeList.size();

		final String personKey = "lalit";
		final Person person = new Person("Lalit Mohan", 55);
		this.service.addPerson(REDIS_HASH_KEY, personKey, person);

		final List<Person> afterList = this.service.getPeople(REDIS_HASH_KEY);
		final int afterSize = afterList.size();

		assertAll(
			() -> assertEquals(beforeSize + 1, afterSize),
			() -> assertFalse(beforeList.contains(person)),
			() -> assertTrue(afterList.contains(person))
		);
	}

	/**
	 * Validates the <code>getPerson</code> method in class
	 * {@link HashPersonRedisService}. It does that by fetching a
	 * {@link Person} object from Redis has against the in-memory
	 * Java map.
	 */
	@Test
	public void testGetPerson() {

		final String personKey = "sumit";
		final Person expectedPerson = PERSON_MAP.get(personKey);
		final Person actualPerson = this.service.getPerson(REDIS_HASH_KEY, personKey);

		assertEquals(expectedPerson, actualPerson);
	}

	/**
	 * Validates the <code>deletePerson</code> method in class
	 * {@link HashPersonRedisService}. It does that by comparing
	 * the before and after state of the Redis hash.
	 */
	@Test
	public void testDeletePerson() {

		final List<Person> beforeList = this.service.getPeople(REDIS_HASH_KEY);
		final int beforeSize = beforeList.size();

		final String personKey = "sumit";
		final Boolean deleteSuccess = this.service.deletePerson(REDIS_HASH_KEY, personKey);
		final Person removedPerson = PERSON_MAP.get(personKey);

		final List<Person> afterList = this.service.getPeople(REDIS_HASH_KEY);
		final int afterSize = afterList.size();

		assertAll(
			() -> assertTrue(deleteSuccess),
			() -> assertEquals(beforeSize - 1, afterSize),
			() -> assertTrue(beforeList.contains(removedPerson)),
			() -> assertFalse(afterList.contains(removedPerson))
		);
	}

	/**
	 * Validates the <code>getPeople</code> method in class
	 * {@link HashPersonRedisService}. It does that by fetching the
	 * contents of the Redis hash and comparing it against the
	 * in-memory Java map.
	 */
	@Test
	public void testGetPeople() {

		final List<Person> expectedList = new ArrayList<>(PERSON_MAP.values());
		final int expectedSize = expectedList.size();

		final List<Person> actualList = this.service.getPeople(REDIS_HASH_KEY);
		final int actualSize = actualList.size();

		assertAll(
			() -> assertEquals(expectedSize, actualSize),
			() -> assertEquals(expectedList, actualList)
		);
	}

	/**
	 * Validates the <code>size</code> method in class
	 * {@link HashPersonRedisService} by comparing its
	 * return value against the size of the in-memory
	 * Java map.
	 */
	public void testSize() {
		final Long expectedSize = (long)PERSON_MAP.size();
		final Long actualSize = this.service.size(REDIS_HASH_KEY);

		assertEquals(expectedSize, actualSize);
	}

	/**
	 * Validates the <code>clear</code> method in class
	 * {@link HashPersonRedisService}.
	 */
	public void testClear() {
		this.service.clear(REDIS_HASH_KEY);

		final List<Person> expectedList = List.of();
		final int expectedSize = expectedList.size();

		final List<Person> actualList = this.service.getPeople(REDIS_HASH_KEY);
		final int actualSize = actualList.size();

		assertAll(
			() -> assertEquals(expectedSize, actualSize),
			() -> assertEquals(expectedList, actualList)
		);
	}
}