package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.SimplePersonRedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SimplePersonRedisServiceUnitTests {

	private final SimplePersonRedisService redisService;

	@Autowired
	public SimplePersonRedisServiceUnitTests(final SimplePersonRedisService redisService) {
		this.redisService = redisService;
	}

	@Test
	public void testAutowiringSuccessful() {
		assertNotNull(this.redisService);
	}

	@Test
	public void testSetPerson() {
		final String key = "person1";
		final Person value = new Person(
			"Amit Batra",
			100
		);

		this.redisService.setPerson(key, value);
		final Person retrievedPerson = this.redisService.getPerson(key);

		assertAll(
			() -> assertNotNull(retrievedPerson),
			() -> assertEquals(retrievedPerson, value)
		);
	}

	@Test
	public void testDeletePerson() {
		final String key = "person2";
		final Person value = new Person(
			"Rahul Batra",
			20
		);

		this.redisService.setPerson(key, value);
		Person deletedPerson = this.redisService.deletePerson(key);

		assertAll(
			() -> assertNotNull(deletedPerson),
			() -> assertEquals(deletedPerson, value)
		);
	}

	@Test
	public void testExpirePerson() {
		final String key = "person3";
		final Person value = new Person(
			"Mohan Kumar",
			25
		);
		final long expiryIntervalInSec = 1L;

		this.redisService.setPerson(key, value);
		this.redisService.expirePerson(key, Duration.ofSeconds(expiryIntervalInSec));

		try {
			final Person beforeExpiry = this.redisService.getPerson(key);
			final Person afterExpiry = CompletableFuture.supplyAsync(() -> {
				try {
					Thread.sleep(Duration.ofSeconds(expiryIntervalInSec + 1L).toMillis());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				return this.redisService.getPerson(key);
			}).get();

			assertAll(
				() -> assertEquals(beforeExpiry, value),
				() -> assertNull(afterExpiry)
			);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}