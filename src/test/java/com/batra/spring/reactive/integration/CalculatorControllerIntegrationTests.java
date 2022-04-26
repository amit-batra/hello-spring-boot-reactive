package com.batra.spring.reactive.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculatorControllerIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void verifyInitialSetup() {
		assertNotNull(this.webTestClient);
	}

	@Test
	public void testAdd() {
		webTestClient.get().uri("/calculator/add/2/3")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Integer.class)
			.isEqualTo(5);
	}

	@Test
	public void testSubtract() {
		webTestClient.get().uri("/calculator/subtract/2/3")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Integer.class)
			.isEqualTo(-1);
	}

	@Test
	public void testMultiply() {
		webTestClient.get().uri("/calculator/multiply/2/3")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Integer.class)
			.isEqualTo(6);
	}

	@Test
	public void testDivide() {
		webTestClient.get().uri("/calculator/divide/2/3")
			.exchange()
			.expectStatus().isOk()
			.expectBody(Integer.class)
			.isEqualTo(0);
	}
}