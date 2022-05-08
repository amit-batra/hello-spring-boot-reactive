package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.ListStringRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}