package com.batra.spring.reactive.integration;

import com.batra.spring.reactive.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EmployeeServiceIntegrationTests {

	private final WebTestClient webTestClient;
	private final Iterable<Employee> testEmployees;

	@Autowired
	public EmployeeServiceIntegrationTests(WebTestClient webTestClient) {
		this.webTestClient = webTestClient;
		this.testEmployees = this.webTestClient.get()
			.uri("/employees")
			.exchange()
			.returnResult(Employee.class)
			.getResponseBody()
			.toIterable();
	}

	@Test
	void verifyInitialSetup() {
		assertAll(
			() -> assertNotNull(this.webTestClient),
			() -> assertNotNull(this.testEmployees)
		);
	}
}