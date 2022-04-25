package com.batra.spring.reactive.contoller;

import com.batra.spring.reactive.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

	@Autowired
	private CalculatorService calculatorService;

	@GetMapping("/add/{first}/{second}")
	public Mono<Integer> add(@PathVariable Integer first, @PathVariable Integer second) {
		return Mono.just(calculatorService.add(first, second));
	}

	@GetMapping("/subtract/{first}/{second}")
	public Mono<Integer> subtract(@PathVariable Integer first, @PathVariable Integer second) {
		return Mono.just(calculatorService.subtract(first, second));
	}

	@GetMapping("/multiply/{first}/{second}")
	public Mono<Integer> multiply(@PathVariable Integer first, @PathVariable Integer second) {
		return Mono.just(calculatorService.multiply(first, second));
	}

	@GetMapping("/divide/{first}/{second}")
	public Mono<Integer> divide(@PathVariable Integer first, @PathVariable Integer second) {
		return Mono.just(calculatorService.divide(first, second));
	}
}