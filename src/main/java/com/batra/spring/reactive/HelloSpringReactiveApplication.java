package com.batra.spring.reactive;

import com.batra.spring.reactive.entity.Department;
import com.batra.spring.reactive.entity.Employee;
import com.batra.spring.reactive.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class HelloSpringReactiveApplication implements CommandLineRunner {

	@Autowired
	private EmployeeService employeeService;

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringReactiveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List.of(
			new Employee("John Doe", "john.doe@springboot.com", Department.HR),
			new Employee("Jane Doe", "jane.doe@springboot.com", Department.IT),
			new Employee("Jack Doe", "jack.doe@springboot.com", Department.FINANCE)
		).forEach(employeeService::saveEmployee);
	}
}