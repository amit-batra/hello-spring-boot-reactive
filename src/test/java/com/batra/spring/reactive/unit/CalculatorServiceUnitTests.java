package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculatorServiceUnitTests {

	@Autowired
	private CalculatorService calculatorService;

	@Test
	public void testAdd() {
		assertEquals(5, calculatorService.add(2, 3));
	}

	@Test
	public void testSubtract() {
		assertEquals(1, calculatorService.subtract(3, 2));
	}

	@Test
	public void testMultiply() {
		assertEquals(6, calculatorService.multiply(2, 3));
	}

	@Test
	public void testDivide() {
		assertEquals(2, calculatorService.divide(4, 2));
	}
}