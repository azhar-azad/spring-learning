package com.azad.employeecrud;

import java.util.List;

public interface EmployeeServices {

    List<Employee> getAllEmployee();
    void save(Employee employee);
    Employee getById(Long id);
    void deleteViaId(Long id);
}
