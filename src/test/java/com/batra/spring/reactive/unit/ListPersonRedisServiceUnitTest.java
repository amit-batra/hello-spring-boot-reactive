package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.ListPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ListPersonRedisServiceUnitTest {

	private final ListPersonRedisService service;

	@Autowired
	public ListPersonRedisServiceUnitTest(final ListPersonRedisService service) {
		this.service = service;
	}

	@BeforeEach
	public void setUp() {
	}

	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.service);
	}

	@Test
	public void testClearList() {
	}

	@Test
	public void testSize() {
	}

	@Test
	public void testGetList() {
	}

	@Test
	public void testAddPersonToLeft() {
	}

	@Test
	public void testAddPersonToRight() {
	}

	@Test
	public void testRemovePersonFromLeft() {
	}

	@Test
	public void testRemovePersonFromRight() {
	}
}