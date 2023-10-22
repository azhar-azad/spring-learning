package com.azad.employeecrud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeServices {

    @Autowired
    private EmployeeRepository empRepo;

    @Override
    public List<Employee> getAllEmployee() {
        return empRepo.findAll();
    }

    @Override
    public void save(Employee employee) {
        empRepo.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        Optional<Employee> optionalEmployee = empRepo.findById(id);
        Employee employee = null;
        if (optionalEmployee.isPresent())
            employee = optionalEmployee.get();
        else
            throw new RuntimeException("Employee not found for id: " + id);
        return employee;
    }

    @Override
    public void deleteViaId(Long id) {
        empRepo.deleteById(id);
    }
}
