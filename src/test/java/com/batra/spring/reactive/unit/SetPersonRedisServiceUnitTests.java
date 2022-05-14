package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.SetPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
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
	public void testAddSuccess() {

		final Set<Person> beforeSet = this.service.getPeople(REDIS_SET_KEY);
		final int beforeSize = beforeSet.size();

		final Person person = new Person("Mohit Mohan", 55);
		final boolean isAddSuccess = this.service.add(REDIS_SET_KEY, person);

		final Set<Person> afterSet = this.service.getPeople(REDIS_SET_KEY);
		final int afterSize = afterSet.size();

		assertAll(
			() -> assertFalse(beforeSet.contains(person)),
			() -> assertTrue(isAddSuccess),
			() -> assertTrue(afterSet.contains(person)),
			() -> assertEquals(beforeSize + 1, afterSize)
		);
	}

	@Test
	public void testAddFailure() {
		final Set<Person> beforeSet = this.service.getPeople(REDIS_SET_KEY);
		final int beforeSize = beforeSet.size();

		final Person person = beforeSet.iterator().next();
		final boolean isAddSuccess = this.service.add(REDIS_SET_KEY, person);

		final Set<Person> afterSet = this.service.getPeople(REDIS_SET_KEY);
		final int afterSize = afterSet.size();

		assertAll(
			() -> assertTrue(beforeSet.contains(person)),
			() -> assertFalse(isAddSuccess),
			() -> assertTrue(afterSet.contains(person)),
			() -> assertEquals(beforeSize, afterSize)
		);
	}

	@Test
	public void testRemoveSuccess() {

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
	public void testRemoveFailure() {

		final Set<Person> beforeSet = this.service.getPeople(REDIS_SET_KEY);
		final int beforeSize = beforeSet.size();

		final Person person = new Person("Unknown Guy", 25);
		final boolean isRemovalSuccess = this.service.remove(REDIS_SET_KEY, person);

		final Set<Person> afterSet = this.service.getPeople(REDIS_SET_KEY);
		final int afterSize = afterSet.size();

		assertAll(
			() -> assertFalse(beforeSet.contains(person)),
			() -> assertFalse(isRemovalSuccess),
			() -> assertFalse(afterSet.contains(person)),
			() -> assertEquals(beforeSize, afterSize)
		);
	}

	@Test
	public void testIsMemberSuccess() {
		final Person person = PERSON_SET.iterator().next();
		final boolean isMemberSuccess = this.service.isMember(REDIS_SET_KEY, person);

		assertTrue(isMemberSuccess);
	}

	@Test
	public void testIsMemberFailure() {
		final Person person = new Person("Unknown Guy", 25);
		final boolean isMemberFailure = this.service.isMember(REDIS_SET_KEY, person);

		assertFalse(isMemberFailure);
	}

	@Test
	public void testSize() {
		final long expectedSize = PERSON_SET.size();
		final Long actualSize = this.service.size(REDIS_SET_KEY);

		assertEquals(expectedSize, actualSize);
	}

	@Test
	public void testDifference() {
		final Set<Person> other = Set.of(
			new Person("Sumit Mohan", 35),
			new Person("Unknown Guy", 25)
		);
		final Set<Person> expectedSet = new HashSet<>(PERSON_SET);
		expectedSet.removeAll(other);
		final int expectedSize = expectedSet.size();

		final String otherKey = "otherSet";
		other.forEach(person ->
			this.service.add(otherKey, person)
		);

		final Set<Person> actualSet = this.service.difference(REDIS_SET_KEY, otherKey);
		final int actualSize = actualSet.size();

		assertAll(
			() -> assertEquals(expectedSize, actualSize),
			() -> assertEquals(expectedSet, actualSet)
		);
	}

	@Test
	public void testIntersection() {
		final Set<Person> other = Set.of(
			new Person("Sumit Mohan", 35),
			new Person("Unknown Guy", 25)
		);
		final Set<Person> expectedSet = new HashSet<>(PERSON_SET);
		expectedSet.retainAll(other);
		final int expectedSize = expectedSet.size();

		final String otherKey = "otherSet";
		other.forEach(person ->
			this.service.add(otherKey, person)
		);

		final Set<Person> actualSet = this.service.intersection(REDIS_SET_KEY, otherKey);
		final int actualSize = actualSet.size();

		assertAll(
			() -> assertEquals(expectedSize, actualSize),
			() -> assertEquals(expectedSet, actualSet)
		);
	}

	@Test
	public void testUnion() {
		final Set<Person> other = Set.of(
			new Person("Sumit Mohan", 35),
			new Person("Unknown Guy", 25)
		);
		final Set<Person> expectedSet = new HashSet<>(PERSON_SET);
		expectedSet.addAll(other);
		final int expectedSize = expectedSet.size();

		final String otherKey = "otherSet";
		other.forEach(person ->
			this.service.add(otherKey, person)
		);

		final Set<Person> actualSet = this.service.union(REDIS_SET_KEY, otherKey);
		final int actualSize = actualSet.size();

		assertAll(
			() -> assertEquals(expectedSize, actualSize),
			() -> assertEquals(expectedSet, actualSet)
		);
	}

	@Test
	public void testGetPeople() {
		// TODO
	}
}