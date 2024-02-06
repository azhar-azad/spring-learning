package com.azad.employeereactive.repository;

import com.azad.employeereactive.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {
}
