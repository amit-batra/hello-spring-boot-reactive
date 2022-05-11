package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.ListPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ListPersonRedisServiceUnitTest {

	private static final String REDIS_LIST_KEY = "peopleList";
	private static final List<Person> LIST_PERSON = List.of(
		new Person("Sumit Mohan", 25),
		new Person("Mohit Mohan", 35),
		new Person("Namit Mohan", 45)
	);

	private final ListPersonRedisService service;

	@Autowired
	public ListPersonRedisServiceUnitTest(final ListPersonRedisService service) {
		this.service = service;
	}

	@BeforeEach
	public void setUp() {
		this.service.clearList(REDIS_LIST_KEY);
		LIST_PERSON.forEach(person ->
			this.service.addPersonToRight(REDIS_LIST_KEY, person)
		);
	}

	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.service);
	}

	@Test
	public void testClearList() {
		this.service.clearList(REDIS_LIST_KEY);

		final List<Person> expectedList = List.of();
		final List<Person> actualList = this.service.getList(REDIS_LIST_KEY);

		assertEquals(expectedList, actualList);
	}

	@Test
	public void testSize() {
		final long expectedSize = LIST_PERSON.size();
		final long actualSize = this.service.size(REDIS_LIST_KEY);

		assertEquals(expectedSize, actualSize);
	}

	@Test
	public void testGetList() {
		final List<Person> expectedList = LIST_PERSON;
		final List<Person> actualList = this.service.getList(REDIS_LIST_KEY);

		assertEquals(expectedList, actualList);
	}

	@Test
	public void testAddPersonToLeft() {
		final Person person = new Person("Kirit Mohan", 15);
		this.service.addPersonToLeft(REDIS_LIST_KEY, person);

		final List<Person> expectedList = new LinkedList<>();
		expectedList.add(person);
		expectedList.addAll(LIST_PERSON);

		final List<Person> actualList = this.service.getList(REDIS_LIST_KEY);
		assertEquals(expectedList, actualList);
	}

	@Test
	public void testAddPersonToRight() {
		final Person person = new Person("Bhagat Mohan", 55);
		this.service.addPersonToRight(REDIS_LIST_KEY, person);

		final List<Person> expectedList = new LinkedList<>(LIST_PERSON);
		expectedList.add(person);

		final List<Person> actualList = this.service.getList(REDIS_LIST_KEY);
		assertEquals(expectedList, actualList);
	}

	@Test
	public void testRemovePersonFromLeft() {

		final Long initialSize = Long.valueOf(LIST_PERSON.size());
		final Person expectedPerson = LIST_PERSON.get(0);

		final Person removedPerson = this.service.removePersonFromLeft(REDIS_LIST_KEY);
		final Long remainingSize = this.service.size(REDIS_LIST_KEY);

		assertAll(
			() -> assertEquals(initialSize - 1, remainingSize),
			() -> assertEquals(expectedPerson, removedPerson)
		);
	}

	@Test
	public void testRemovePersonFromRight() {

		final Long initialSize = Long.valueOf(LIST_PERSON.size());
		final Person expectedPerson = LIST_PERSON.get(LIST_PERSON.size() - 1);

		final Person removedPerson = this.service.removePersonFromRight(REDIS_LIST_KEY);
		final Long remainingSize = this.service.size(REDIS_LIST_KEY);

		assertAll(
			() -> assertEquals(initialSize - 1, remainingSize),
			() -> assertEquals(expectedPerson, removedPerson)
		);
	}
}