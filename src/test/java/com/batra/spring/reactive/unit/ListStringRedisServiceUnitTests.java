package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.ListStringRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ListStringRedisServiceUnitTests {

	private final ListStringRedisService service;
	private static final String LIST_KEY = "listKey";
	final static List<String> STRING_LIST = List.of(
		"one",
		"two",
		"three"
	);

	@Autowired
	public ListStringRedisServiceUnitTests(final ListStringRedisService service) {
		this.service = service;
	}

	/**
	 * Before each test is executed, this method will initialize a Redis list
	 * with the key named "listKey" with elements ["one, "two", three"].
	 */
	@BeforeEach
	public void setup() {
		this.service.clearList(LIST_KEY);
		STRING_LIST.forEach(
			value -> this.service.addValueToRight(LIST_KEY, value)
		);
	}

	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.service);
	}

	@Test
	public void testGetListForExistingList() {
		List<String> listFromRedis = this.service.getList(LIST_KEY);
		assertAll(
			() -> assertNotNull(listFromRedis),
			() -> assertEquals(STRING_LIST.size(), listFromRedis.size()),
			() -> assertEquals(STRING_LIST, listFromRedis)
		);
	}

	@Test
	public void testGetListForNonExistingList() {
		final String nonExistingListKey = "nonExistingListKey";
		List<String> listFromRedis = this.service.getList(nonExistingListKey);
		assertEquals(0, listFromRedis.size());
	}

	@Test
	public void testAddValueToLeft() {
		final String leftmostElement = "zero";
		final List<String> expectedList = new LinkedList<>();
		expectedList.add(leftmostElement);
		expectedList.addAll(STRING_LIST);

		this.service.addValueToLeft(LIST_KEY, leftmostElement);
		final List<String> listFromRedis = this.service.getList(LIST_KEY);

		assertEquals(expectedList, listFromRedis, "Lists don't match");
	}

	@Test
	public void testAddValueToRight() {
		final String rightmostElement = "four";
		final List<String> expectedList = new LinkedList<>(STRING_LIST);
		expectedList.add(rightmostElement);

		this.service.addValueToRight(LIST_KEY, rightmostElement);
		final List<String> listFromRedis = this.service.getList(LIST_KEY);

		assertEquals(expectedList, listFromRedis, "Lists don't match");
	}

	@Test
	public void testRemoveValueFromLeft() {
		final List<String> expectedList = new LinkedList<>(STRING_LIST);
		final String expectedElement = expectedList.remove(0);

		final String valueFromLeft = this.service.removeValueFromLeft(LIST_KEY);
		final List<String> listFromRedis = this.service.getList(LIST_KEY);

		assertAll(
			() -> assertEquals(expectedElement, valueFromLeft),
			() -> assertEquals(expectedList.size(), listFromRedis.size()),
			() -> assertEquals(expectedList, listFromRedis)
		);
	}

	@Test
	public void testRemoveValueFromRight() {
		final List<String> expectedList = new ArrayList<>(STRING_LIST);
		final String expectedElement = expectedList.remove(expectedList.size() - 1);

		final String valueFromRight = this.service.removeValueFromRight(LIST_KEY);
		final List<String> listFromRedis = this.service.getList(LIST_KEY);

		assertAll(
			() -> assertEquals(expectedElement, valueFromRight),
			() -> assertEquals(expectedList.size(), listFromRedis.size()),
			() -> assertEquals(expectedList, listFromRedis)
		);
	}
}