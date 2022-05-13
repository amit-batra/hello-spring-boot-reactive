package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.SetPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SetPersonRedisServiceUnitTests {

	private final SetPersonRedisService service;

	@Autowired
	public SetPersonRedisServiceUnitTests(final SetPersonRedisService service) {
		this.service = service;
	}

	@BeforeEach
	public void setUp() {
		// TODO
	}

	@Test
	public void testAdd() {
		// TODO
	}

	@Test
	public void testRemove() {
		// TODO
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