package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.SimpleStringRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SimpleStringRedisServiceUnitTests {

	private final SimpleStringRedisService redisService;

	@Autowired
	public SimpleStringRedisServiceUnitTests(SimpleStringRedisService redisService) {
		this.redisService = redisService;
	}

	@BeforeEach
	public void setup() {
		this.redisService.deleteAllValues();
	}

	@Test
	public void validateAutoWiringWorked() {
		assertNotNull(this.redisService, "SimpleStringRedisService was not Autowired");
	}

	@Test
	public void testGetSetSimpleValue() {
		final String key = "key1";
		final String value = "value1";

		boolean resultBefore = this.redisService.checkIfValueExists(key, value);
		this.redisService.setValue(key, value);
		boolean resultAfter = this.redisService.checkIfValueExists(key, value);

		assertAll(
			() -> assertFalse(resultBefore, "Before check failed"),
			() -> assertTrue(resultAfter, "After check failed")
		);
	}

	@Test
	public void testDeleteSimpleValue() {
		final String key = "key2";
		final String value = "value2";

		boolean result1 = this.redisService.checkIfValueExists(key, value);
		this.redisService.setValue(key, value);
		boolean result2 = this.redisService.checkIfValueExists(key, value);
		String valueOut = this.redisService.deleteValue(key);
		boolean result3 = this.redisService.checkIfValueExists(key, value);

		assertAll(
			() -> assertFalse(result1, "Check on result1 failed"),
			() -> assertTrue(result2, "Check on result2 failed"),
			() -> assertFalse(result3, "Check on result3 failed"),
			() -> assertEquals(value, valueOut, "Check on value failed")
		);
	}

	@Test
	public void testAddIfKeyMissingReturnsTrue() {
		final String key = "key3";
		final String value = "value3";

		// First validate that the key is missing
		assertFalse(this.redisService.checkIfValueExists(key, value));

		// Now validate that the key/value got added
		assertTrue(this.redisService.addIfKeyMissing(key, value));

		// Now validate that key/value exist
		assertTrue((this.redisService.checkIfValueExists(key, value)));
	}

	@Test
	public void testAddIfKeyMissingReturnsFalse() {
		final String key = "key4";
		final String value1 = "value4.1";
		final String value2 = "value4.2";

		// First, insert <key, value1> in Redis
		this.redisService.setValue(key, value1);

		// Validate that adding <key, value2> conditionally returns false
		assertFalse(this.redisService.addIfKeyMissing(key, value2));

		// Next validate that <key, value1> did not get overwritten with
		// <key, value2> in the previous call
		assertEquals(value1, this.redisService.getValueForKey(key));
	}

	@Test
	public void testIncrementValue() {
		final String key = "key5";
		final String value = "1";

		this.redisService.setValue(key, value);
		Long incrementedValue = this.redisService.incrementValue(key);

		assertEquals(2, incrementedValue);
	}
}