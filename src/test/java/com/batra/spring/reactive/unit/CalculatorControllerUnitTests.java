package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.contoller.CalculatorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class CalculatorControllerUnitTests {

	@Autowired
	private CalculatorController calculatorController;

	@Test
	public void testAdd() {
		StepVerifier.create(
			calculatorController.add(2, 3)
		).expectNext(5).verifyComplete();
	}

	@Test
	public void testSubtract() {
		StepVerifier.create(
			calculatorController.subtract(2, 3)
		).expectNext(-1).verifyComplete();
	}

	@Test
	public void testMultiply() {
		StepVerifier.create(
			calculatorController.multiply(2, 3)
		).expectNext(6).verifyComplete();
	}

	@Test
	public void testDivide() {
		StepVerifier.create(
			calculatorController.divide(2, 3)
		).expectNext(0).verifyComplete();
	}
}