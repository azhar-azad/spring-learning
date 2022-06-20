package com.azad.payroll.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select count(*) from employee", nativeQuery = true)
    long count();
}
