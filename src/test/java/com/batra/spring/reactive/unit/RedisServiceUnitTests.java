package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RedisServiceUnitTests {

	private final RedisService redisService;

	@Autowired
	public RedisServiceUnitTests(RedisService redisService) {
		this.redisService = redisService;
	}

	@BeforeEach
	public void setup() {
		this.redisService.deleteAllValues();
	}

	@Test
	public void validateAutoWiringWorked() {
		assertNotNull(this.redisService, "RedisService was not Autowired");
	}

	@Test
	public void testGetSetSimpleValue() {
		final String inKey = "key1";
		final String inValue = "value1";

		boolean resultBefore = this.redisService.checkIfValueExists(inKey, inValue);
		this.redisService.setValue(inKey, inValue);
		boolean resultAfter = this.redisService.checkIfValueExists(inKey, inValue);

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
}