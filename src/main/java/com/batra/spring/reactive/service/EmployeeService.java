package com.batra.spring.reactive.service;

import com.batra.spring.reactive.entity.Employee;
import com.batra.spring.reactive.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void deleteEmployeeById(Long id) {
		employeeRepository.deleteById(id);
	}

	public void deleteAllEmployees() {
		employeeRepository.deleteAll();
	}

	public long getEmployeeCount() {
		return employeeRepository.count();
	}
}