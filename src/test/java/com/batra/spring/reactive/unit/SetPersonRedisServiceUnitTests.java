package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.SetPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SetPersonRedisServiceUnitTests {

	private final SetPersonRedisService service;

	private static final String REDIS_SET_KEY = "personSet";
	private static final Set<Person> PERSON_SET = Set.of(
		new Person("Amit Mohan", 25),
		new Person("Sumit Mohan", 35),
		new Person("Namit Mohan", 45)
	);

	@Autowired
	public SetPersonRedisServiceUnitTests(final SetPersonRedisService service) {
		this.service = service;
	}

	/**
	 * The <code>setUp</code> method is executed before each test case.
	 * In this method, the Redis set is being re-initialized from the
	 * in-memory Java set.
	 */
	@BeforeEach
	public void setUp() {
		this.service.clear(REDIS_SET_KEY);
		PERSON_SET.forEach(person -> {
			this.service.add(REDIS_SET_KEY, person);
		});
	}

	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.service);
	}

	@Test
	public void testAdd() {

		final Set<Person> beforeSet = this.service.getPeople(REDIS_SET_KEY);
		final int beforeSize = beforeSet.size();

		final Person person = new Person("Mohit Mohan", 55);
		this.service.add(REDIS_SET_KEY, person);

		final Set<Person> afterSet = this.service.getPeople(REDIS_SET_KEY);
		final int afterSize = afterSet.size();

		assertAll(
			() -> assertFalse(beforeSet.contains(person)),
			() -> assertTrue(afterSet.contains(person)),
			() -> assertEquals(beforeSize + 1, afterSize)
		);
	}

	@Test
	public void testRemove() {
		final Set<Person> beforeSet = this.service.getPeople(REDIS_SET_KEY);
		final int beforeSize = beforeSet.size();

		final Person person = beforeSet.iterator().next();
		final boolean isRemovalSuccess = this.service.remove(REDIS_SET_KEY, person);

		final Set<Person> afterSet = this.service.getPeople(REDIS_SET_KEY);
		final int afterSize = afterSet.size();

		assertAll(
			() -> assertTrue(beforeSet.contains(person)),
			() -> assertTrue(isRemovalSuccess),
			() -> assertFalse(afterSet.contains(person)),
			() -> assertEquals(beforeSize - 1, afterSize)
		);
	}

	@Test
	public void testIsMember() {
		// TODO
	}

	@Test
	public void testSize() {
		// TODO
	}

	@Test
	public void testDifference() {
		// TODO
	}

	@Test
	public void testIntersection() {
		// TODO
	}

	@Test
	public void testUnion() {
		// TODO
	}

	@Test
	public void testGetPeople() {
		// TODO
	}
}