package com.batra.spring.reactive.repository;

import com.batra.spring.reactive.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}