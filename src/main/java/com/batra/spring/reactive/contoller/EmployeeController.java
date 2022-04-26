package com.batra.spring.reactive.contoller;

import com.batra.spring.reactive.entity.Employee;
import com.batra.spring.reactive.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public Flux<Employee> getAllEmployees() {
		return Flux.fromIterable(employeeService.getAllEmployees());
	}

	@GetMapping("/{id}")
	public Mono<Employee> getEmployeeById(@PathVariable Long id) {
		return Mono.justOrEmpty(employeeService.getEmployeeById(id));
	}

	@DeleteMapping("/{id}")
	public Mono<Employee> deleteEmployeeById(@PathVariable Long id) {

		Mono<Employee> employeeMono = Mono.justOrEmpty(employeeService.getEmployeeById(id));
		employeeService.deleteEmployeeById(id);

		return employeeMono;
	}

	@PostMapping
	public Mono<Employee> addEmployee(@RequestBody Employee employee) {
		return Mono.justOrEmpty(employeeService.saveEmployee(employee));
	}
}