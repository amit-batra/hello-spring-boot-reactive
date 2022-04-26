package com.batra.spring.reactive.unit;

import com.batra.spring.reactive.entity.Department;
import com.batra.spring.reactive.entity.Employee;
import com.batra.spring.reactive.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class EmployeeServiceUnitTests {
	@Autowired
	private EmployeeService employeeService;

	private List<Employee> testEmployees = List.of(
		new Employee("John Doe", "john.doe@springboot.com", Department.HR),
		new Employee("Jane Doe", "jane.doe@springboot.com", Department.IT),
		new Employee("Jack Doe", "jack.doe@springboot.com", Department.FINANCE)
	);

	@BeforeEach
	public void setUp() {
		testEmployees.forEach(employeeService::saveEmployee);
	}

	@AfterEach
	public void tearDown() {
		employeeService.deleteAllEmployees();
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
		assertEquals(testEmployees.size(), count);
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