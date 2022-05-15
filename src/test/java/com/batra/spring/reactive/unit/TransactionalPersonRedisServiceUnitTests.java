package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Person;
import com.batra.spring.reactive.service.ListPersonRedisService;
import com.batra.spring.reactive.service.TransactionalPersonRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TransactionalPersonRedisServiceUnitTests {

	private final TransactionalPersonRedisService transactionalService;
	private final ListPersonRedisService listService;

	private static final String REDIS_LIST_KEY = "transactionalList";
	private static final List<Person> PERSON_LIST = List.of(
		new Person("Amit Mohan", 25),
		new Person("Sumit Mohan", 35),
		new Person("Mohit Mohan", 45)
	);

	@Autowired
	public TransactionalPersonRedisServiceUnitTests(
		final TransactionalPersonRedisService transactionalService,
		final ListPersonRedisService listService
	) {
		this.transactionalService = transactionalService;
		this.listService = listService;
	}

	@BeforeEach
	public void setUp() {
		this.listService.clearList(REDIS_LIST_KEY);
		PERSON_LIST.forEach(person ->
			this.listService.addPersonToRight(REDIS_LIST_KEY, person)
		);
	}

	@Test
	public void testAutowiringSuccessful() {
		assertAll(
			() -> assertNotNull(this.transactionalService),
			() -> assertNotNull(this.listService)
		);
	}

	@Test
	public void testTransactionCommitWithAdd() {

		final List<Person> beforeList = this.listService.getList(REDIS_LIST_KEY);
		final int beforeSize = beforeList.size();

		final Person person = new Person("Namit Mohan", 55);
		final List<Object> results = this.transactionalService.executeInTransaction(new SessionCallback<>() {

			public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();
				operations.opsForList().rightPush((K) REDIS_LIST_KEY, (V) person);
				return operations.exec();
			}
		});
		final int nOperations = results.size();
		final Object singleResult = results.iterator().next();

		final List<Person> afterList = this.listService.getList(REDIS_LIST_KEY);
		final int afterSize = afterList.size();

		assertAll(
			() -> assertEquals(beforeSize + 1, afterSize),
			() -> assertFalse(beforeList.contains(person)),
			() -> assertTrue(afterList.contains(person)),
			() -> assertEquals(1, nOperations),
			() -> assertEquals(Long.class, singleResult.getClass()),
			() -> assertEquals(afterSize, ((Long) singleResult).intValue())
		);
	}

	@Test
	public void testTransactionCommitWithRemove() {

		final List<Person> beforeList = this.listService.getList(REDIS_LIST_KEY);
		final int beforeSize = beforeList.size();

		List<Object> results = this.transactionalService.executeInTransaction(new SessionCallback<>() {

			public <K, V> List<Object> execute(RedisOperations<K, V> operations) throws DataAccessException {
				operations.multi();
				operations.opsForList().leftPop((K) REDIS_LIST_KEY);
				return operations.exec();
			}
		});

		final int nOperations = results.size();
		final Person person = (Person) results.iterator().next();

		final List<Person> afterList = this.listService.getList(REDIS_LIST_KEY);
		final int afterSize = afterList.size();

		assertAll(
			() -> assertEquals(beforeSize - 1, afterSize),
			() -> assertTrue(beforeList.contains(person)),
			() -> assertFalse(afterList.contains(person)),
			() -> assertEquals(1, nOperations)
		);
	}

	@Test
	public void testTransactionRollback() {

	}
}