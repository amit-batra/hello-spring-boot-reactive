package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Department;
import com.batra.spring.reactive.entity.Employee;
import com.batra.spring.reactive.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class EmployeeServiceUnitTests {
	private final EmployeeService employeeService;
	private long numberOfTestEmployees;

	@Autowired
	public EmployeeServiceUnitTests(EmployeeService employeeService) {
		this.employeeService = employeeService;
		final Iterable<Employee> testEmployees = employeeService.getAllEmployees();
		testEmployees.forEach(employee -> numberOfTestEmployees++);
	}

	@Test
	public void testGetAllEmployees() {
		Iterable<Employee> allEmployees = employeeService.getAllEmployees();
		assertNotNull(allEmployees);
		allEmployees.forEach(employee -> {
			System.out.printf("Found employee: %s%n", employee);
		});
	}

	@Test
	public void testDeleteEmployeeById() {
		Iterable<Employee> allEmployees = employeeService.getAllEmployees();
		Employee toBeDeleted = allEmployees.iterator().next();

		employeeService.deleteEmployeeById(toBeDeleted.getId());
		Employee wasDeleted = employeeService.getEmployeeById(toBeDeleted.getId());
		assertNull(wasDeleted);
	}

	@Test
	public void testDeleteAllEmployees() {
		employeeService.deleteAllEmployees();
		long count = employeeService.getEmployeeCount();
		Iterable<Employee> allEmployees = employeeService.getAllEmployees();

		assertAll(
			() -> assertEquals(0, count),
			() -> assertFalse(allEmployees.iterator().hasNext())
		);
	}

	@Test
	void testGetEmployeeCount() {
		long count = employeeService.getEmployeeCount();
		assertEquals(numberOfTestEmployees, count);
	}

	@Test
	void testAddEmployee() {
		Employee newEmployee = new Employee("Jim Doe", "jim.doe@springboot.com", Department.SALES);
		Employee savedEmployee = employeeService.saveEmployee(newEmployee);
		assertAll(
			() -> assertNotNull(savedEmployee),
			() -> assertNotNull(savedEmployee.getId()),
			() -> assertEquals(newEmployee.getName(), savedEmployee.getName()),
			() -> assertEquals(newEmployee.getEmail(), savedEmployee.getEmail()),
			() -> assertEquals(newEmployee.getDepartment(), savedEmployee.getDepartment())
		);
	}
}